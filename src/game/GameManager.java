package game;

import game.users.User;
import gui.ChosePiece;
import gui.GuiManager;
import gui.images.Field;
import socket.LocalClient;
import socket.packages.Packet;
import socket.packages.PacketType;
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

        Board.instance.onMoved.add((Piece piece) -> {
            lastMovedPiece = piece;
        });
        ChosePiece.onTypeSelected.add((Piece.Type newType) -> {
            if (lastMovedPiece == null)
                return;
            Board.instance.networkPromotePiece(lastMovedPiece, newType);
            GuiManager.updateField(lastMovedPiece.getPosition());
        });

        Board.instance.onPromotion.addAction(() -> {
            if (localUser.canPlay()) {
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

    private static Piece lastMovedPiece = null;

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

        PacketType type = PacketType.fromCode(packet.getPackedBuffer());

        String[] values;
        switch (type) {
            case MOVE:
                Move move = new Move(new Vector(), new Vector());
                move.unpack(packet.getBufferData());
                Board.instance.move(move);
                break;
            case CHANGE_TYPE:
                Vector at = new Vector();
                Piece.Type newType = null;
                values = packet.getBufferData().split("~");

                if (values.length == 2) {
                    try {
                        at.unpack(values[0]);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    newType = Piece.Type.fromCode(Integer.parseInt(values[1]));
                } else
                    throw new IllegalArgumentException("Invalid buffer format");

                Board.instance.promotePiece(Board.instance.get(at), newType);
                GuiManager.updateField(at);
                break;
            case CUSTOM:
                break;
            case SEND_PLAYER:
                LocalClient.instance.send(new Packet(PacketType.PLAYER, localUser.pack()));
                break;
            case PLAYER:
                opponent = new User(packet.getBufferData());
                break;
            case START_GAME:
                newGame();
                break;
            case CHANGE_COLOR:
                localUser.changeSide();
                break;
            case DISCONNECT:
                break;
            default:
                break;
        }
    }
}