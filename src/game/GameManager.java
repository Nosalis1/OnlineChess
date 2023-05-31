package game;

import audio.AudioManager;
import gui.ColorGradient;
import gui.Game;
import gui.images.Field;
import gui.images.Image;
import socket.packages.Packet;
import util.Array;
import util.Console;
import util.Vector;

public class GameManager {
    public static GameManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new GameManager();
    }

    public GameManager() {
        Field.onFieldClicked.add(this::onFieldClicked);

        Board.instance.onPieceEaten.add(this::onPieceEaten);
        Board.instance.onPieceMoved.add(this::onPieceMoved);
        Board.instance.onPieceMove.add(this::onPieceMove);

        Board.instance.reset();
        updateAll();
    }

    public void updateAll() {
        util.Array<Piece> allPieces = Board.instance.getAllPieces();

        allPieces.foreach((Piece piece) -> {
            util.Vector at = piece.getPosition();

            Game.instance.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
        });
    }

    private void onPieceEaten(Piece piece) {
        util.Vector at = piece.getPosition();

        Game.instance.getField(at).setImage(null);
    }

    private void onPieceMoved(Piece piece) {
        util.Vector at = piece.getPosition();

        Game.instance.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);

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

    private void onPieceMove(Move move) {
        util.Vector at = move.getFrom();
        Game.instance.getField(at).setImage(null);
    }

    private final Array<Field> currentHighlights = new Array<>();

    private void resetHighlights() {
        currentHighlights.foreach((Field field) -> {
            field.setColor(ColorGradient.FIELD.getColor(field.isGradient()));
        });
        currentHighlights.clear();
    }

    private void setSelected(Vector at) {
        resetHighlights();

        Field temp = Game.instance.getField(at);
        temp.setColor(ColorGradient.HIGHLIGHT.getColor(temp.isGradient()));
        currentHighlights.add(temp);

        setMoves(at);
    }

    private void setMoves(Vector at) {
        Piece piece = Board.instance.get(at);
        if (piece == null)
            return;

        Array<Vector> moves = piece.getMoves();

        moves.foreach((Vector position) -> {
            Field temp = Game.instance.getField(position);

            temp.setColor(Board.instance.isNull(position) ? ColorGradient.MOVE.getColor(temp.isGradient()) : ColorGradient.ATTACK.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        });
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

    private void handleClicked(Vector at) {
        if (!canPlay())
            return;
        if (selected == null && (!Board.instance.isNull(at) && Board.instance.get(at).isColor(isWhite() ? Piece.Color.White : Piece.Color.Black))) {
            selected = at;
            setSelected(at);
        } else if (selected == at) {
            selected = null;
            resetHighlights();
        } else if (selected != null) {
            Board.instance.tryMove(selected, at);
            selected = null;
            resetHighlights();
        }
    }

    private void onFieldClicked(Vector at) {
        handleClicked(at);
    }

    Move networkMove = new Move(Vector.ZERO, Vector.ZERO);

    public void handleNetworkPackage(final Packet packet) {

        if (packet.equals(Packet.START_GAME)) {
            Game.instance.showWindow();
            AudioManager.playClip(3);

            System.out.println(isWhite());
        } else if (packet.equals(Packet.CHANGE_COLOR)) {
            changeColor();
        } else if (packet.equals(Packet.DISCONNECTED)) {
            //TODO:HANDLE DISCONNECTED
        } else {
            //TODO:HANDLE CUSTOM PACKAGE
            Console.warning(packet.getBuffer());
            //packet.unpack(networkMove);//TODO:FIX THIS (RETURN CONSTANT VALUE)

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