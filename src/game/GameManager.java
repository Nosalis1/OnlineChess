package game;

import audio.AudioManager;
import game.users.User;
import gui.Game;
import gui.GuiManager;
import gui.images.Field;
import socket.packages.Packet;
import util.Console;
import util.Vector;

import java.io.IOException;

public class GameManager {
    public static GameManager instance;

    public static void initialize() {
        util.Console.message("Initializing GameManager.",Console.PrintType.Main);

        if (instance == null)
            instance = new GameManager();

        try {
            User.loadUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public GameManager() {
        Field.onFieldClicked.add(this::onFieldClicked);

        Board.instance.onPieceMoved.add(this::onPieceMoved);
    }

    public void newGame() {
        Board.instance.reset();
        GuiManager.instance.startGame();
        //TODO:TELL AUDIO MANAGER TO PLAY SOUND
    }

    private void onPieceMoved(Piece piece) {
        nextTurn();

        if (Board.instance.isCheckmate(Piece.Color.White)) {
            System.out.println("WHITE IN CHECKMATE");
            //TODO:ENDGAME
        } else {
            if (Board.instance.isCheck(Piece.Color.White)) {
                System.out.println("WHITE IN CHECK");
            }

            if (Board.instance.isCheckmate(Piece.Color.Black)) {
                System.out.println("BLACK IN CHECKMATE");
                //TODO:ENDGAME
            } else {
                if (Board.instance.isCheck(Piece.Color.Black)) {
                    System.out.println("BLACK IN CHECK");
                }
            }
        }
    }

    private boolean white = true;

    public void changeColor() {
        this.white = !this.white;
    }

    public boolean isWhite() {
        return this.white;
    }

    private boolean whiteTurn = true;

    public boolean isWhiteTurn() {
        return this.whiteTurn;
    }

    public void nextTurn() {
        whiteTurn = !whiteTurn;
    }

    public boolean canPlay() {
        return (isWhite() && isWhiteTurn()) || (!isWhite() && !isWhiteTurn());
    }

    // TODO: add getUsername

    Vector selected;

    private void onFieldClicked(Vector at) {
        if (!canPlay())
            return;
        if (selected == null && (!Board.instance.isNull(at) && Board.instance.get(at).isColor(isWhite() ? Piece.Color.White : Piece.Color.Black))) {
            selected = at;
            GuiManager.instance.onFieldClicked(at);//TODO:FIX THIS IMPLEMENTATION LATER
        } else if (selected == at) {
            selected = null;
            GuiManager.instance.resetHighlights();//TODO:FIX THIS IMPLEMENTATION LATER
        } else if (selected != null) {
            Board.instance.tryMove(selected, at);
            selected = null;
            GuiManager.instance.resetHighlights();//TODO:FIX THIS IMPLEMENTATION LATER
        }
    }

    Move networkMove = new Move(Vector.ZERO, Vector.ZERO);

    public void handleNetworkPackage(final Packet packet) {

        if (packet.equals(Packet.START_GAME)) {
            newGame();
        } else if (packet.equals(Packet.CHANGE_COLOR)) {
            changeColor();
        } else if (packet.equals(Packet.DISCONNECTED)) {
            return;//TODO:HANDLE DISCONNECTED
        } else {
            //TODO:HANDLE CUSTOM PACKAGE
            Vector from = new Vector(), to = new Vector();
            String[] values = packet.getBuffer().split("~");

            if (values.length == 2) {
                from.unapck(values[0]);
                to.unapck(values[1]);
            } else
                throw new IllegalArgumentException("Invalid buffer format");


            Board.instance.move(from, to);
        }
    }
}