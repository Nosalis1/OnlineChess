package game;

import game.users.User;
import gui.GuiManager;
import gui.images.Field;
import socket.packages.Packet;
import util.Vector;

import java.io.IOException;

public class GameManager {
    public static GameManager instance;

    public static void initialize() {

        if (instance == null)
            instance = new GameManager();

        util.Console.message("Initializing GameManager.", instance);

        try {
            User.loadUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public GameManager() {
        Field.onFieldClicked.add(this::onFieldClicked);

        Board.instance.onPieceMoved.add(this::onPieceMoved);
        Board.instance.onPieceEaten.add(this::onPieceEaten);
    }

    private int blackScore,whiteScore;
    public int getBlackScore(){
        return blackScore;
    }
    public  int getWhiteScore() {
        return whiteScore;
    }
    final int[] PIECE_SCORES = {
            5, 3, 3, 9, 0, 1
    };

    private void onPieceEaten(Piece piece) {
        if (piece.isColor(Piece.Color.White)) {
            whiteScore -= PIECE_SCORES[piece.getTypeCode() - 1];
        } else {
            blackScore -= PIECE_SCORES[piece.getTypeCode() - 1];
        }
    }

    public void newGame() {
        Board.instance.reset();
        GuiManager.instance.startGame();

        blackScore = 39;
        whiteScore = 39;
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

    public void handleNetworkPackage(final Packet packet) {

        if (packet.getType() == Packet.Type.MOVE) {
            Vector from = new Vector(), to = new Vector();
            String[] values = packet.getBuffer().split("~");

            if (values.length == 2) {
                from.unapck(values[0]);
                to.unapck(values[1]);
            } else
                throw new IllegalArgumentException("Invalid buffer format");

            Board.instance.move(from, to);
        } else if (packet.getType() == Packet.Type.CUSTOM) {
            return;//TODO:HANDLE CUSTOM
        } else if (packet.getType() == Packet.Type.START_GAME) {
            newGame();
        } else if (packet.getType() == Packet.Type.CHANGE_COLOR) {
            changeColor();
        } else if (packet.getType() == Packet.Type.DISCONNECT) {
            return;//TODO:HANDLE DISCONNECTED
        }
    }
}