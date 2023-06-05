package game;

import game.users.User;
import gui.GuiManager;
import gui.images.Field;
import socket.LocalClient;
import socket.packages.Packet;
import util.Vector;
import util.events.Event;

import java.io.IOException;

public abstract class GameManager {
    private static boolean initialized = false;

    public static boolean isInitialized() {
        return initialized;
    }

    public static void initialize() {
        if (initialized)
            return;

        try {
            User.loadUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Field.onFieldClicked.add(GameManager::onFieldClicked);

        Board.instance.onCheck.add((Piece.Color color) -> {
            System.out.println(color.toString() + " in check!");
        });
        Board.instance.onCheckMate.add((Piece.Color color) -> {
            System.out.println(color.toString() + " in checkMate!");
            onGameEnded.run();
        });

        Board.instance.onPromotion.add(() -> {
            if(localUser.canPlay()){
                GuiManager.getGameWindow().setEnabled(false);
                GuiManager.getChosePieceWindow().showWindow();
            }
        });

        initialized = true;
    }

    public static util.events.Event onGameStarted = new Event();
    public static util.events.Event onGameEnded = new Event();

    public static void newGame() {
        Board.instance.reset();
        onGameStarted.run();
    }

    public static User localUser = null;
    public static User opponent = null;//TODO:ASSIGN OPPONENT

    static Vector selected;

    private static void onFieldClicked(Vector at) {
        if (!localUser.canPlay())
            return;
        if (selected == null && (!Board.instance.isNull(at) && Board.instance.get(at).isColor(localUser.isWhite() ? Piece.Color.White : Piece.Color.Black))) {
            selected = at;
            GuiManager.onFieldClicked(at);
        } else if (selected == at) {
            selected = null;
            GuiManager.resetHighlights();
        } else if (selected != null) {
            Board.instance.tryMove(selected, at);
            selected = null;
            GuiManager.resetHighlights();
        }
    }

    public static void handleNetworkPackage(final Packet packet) {

        if (packet == null)
            return;

        if (packet.getType() == Packet.Type.MOVE) {
            Vector from = new Vector(), to = new Vector();
            String[] values = packet.getBuffer().split("~");

            if (values.length == 2) {
                from.unapck(values[0]);
                to.unapck(values[1]);
            } else
                throw new IllegalArgumentException("Invalid buffer format");

            Board.instance.move(from, to);
        } else if (packet.getType() == Packet.Type.CHANGE_TYPE) {
            System.out.println("CHANGE TYPE RECEIVED");
            Vector at = new Vector();
            Piece.Type newType = null;
            String[] values = packet.getBuffer().split("~");

            if (values.length == 2) {
                at.unapck(values[0]);
                newType = Piece.Type.fromCode(Integer.parseInt(values[1]));
            } else
                throw new IllegalArgumentException("Invalid buffer format");

            System.out.println("CHANGING TYPE <" + at.toString() + "> <" + newType.toString() + ">");
            Board.instance.networkChangePiece(Board.instance.get(at), newType);//TODO:FIX THIS
            GuiManager.updateField(at);
        } else if (packet.getType() == Packet.Type.CUSTOM) {
            return;//TODO:HANDLE CUSTOM
        } else if (packet.getType() == Packet.Type.SEND_PLAYER) {
            LocalClient.instance.send(new Packet(localUser.pack(Packet.Type.PLAYER), Packet.Type.PLAYER));
        } else if (packet.getType() == Packet.Type.PLAYER) {
            opponent = new User(packet.getBuffer());
        } else if (packet.getType() == Packet.Type.START_GAME) {
            newGame();
        } else if (packet.getType() == Packet.Type.CHANGE_COLOR) {
            localUser.changeSide();
        } else if (packet.getType() == Packet.Type.DISCONNECT) {
            return;//TODO:HANDLE DISCONNECTED
        }
    }
}