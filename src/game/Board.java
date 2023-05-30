package game;

import socket.LocalClient;
import socket.packages.Packet;
import util.Array;
import util.Vector;
import util.events.ArgEvent;

public class Board {

    public static final int SIZE = 8;
    public static final int HALF_SIZE = SIZE / 2;
    public static final int LAST = SIZE - 1;

    private static final Piece[][] pieces = new Piece[SIZE][SIZE];

    private static final util.Array<Piece> whitePieces = new Array<>();
    private static final util.Array<Piece> blackPieces = new Array<>();
    private static final util.Array<Piece> allPieces = new Array<>();

    public static void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pieces[i][j] = null;
            }
        }
    }

    private static void addPiece(final Piece newPiece) {
        final Vector position = newPiece.getPosition();
        pieces[position.X][position.Y] = newPiece;

        allPieces.add(newPiece);

        if (newPiece.isColor(Piece.Color.White))
            whitePieces.add(newPiece);
        else
            blackPieces.add(newPiece);

    }

    public static void reset() {
        clear();

        final int[] order = {1, 2, 3, 4, 5, 3, 2, 1};
        for (int i = 0; i < SIZE; i++) {
            // Setup white pieces
            addPiece(new Piece(Piece.Color.White, Piece.Type.fromCode(order[i]), new util.Vector(0, i)));
            addPiece(new Piece(Piece.Color.White, Piece.Type.Pawn, new util.Vector(1, i)));

            // Setup black pieces
            addPiece(new Piece(Piece.Color.Black, Piece.Type.fromCode(order[i]), new util.Vector(LAST, i)));
            addPiece(new Piece(Piece.Color.Black, Piece.Type.Pawn, new util.Vector(LAST - 1, i)));
        }
    }

    private static void onPieceEaten(Piece piece) {
        if (piece.isColor(Piece.Color.White)) {
            if (whitePieces.contains(piece))
                whitePieces.remove(piece);
        } else {
            if (blackPieces.contains(piece))
                blackPieces.remove(piece);
        }
        allPieces.remove(piece);

        onPieceEaten.run(piece);
    }

    public static Piece get(final int x, final int y) {
        return pieces[x][y];
    }

    public static Piece get(final Vector position) {
        return get(position.X, position.Y);
    }

    public static Piece get(final Piece.Color color, final Piece.Type type, final int offset) {
        int off = offset;

        util.Array<Piece> currentPieces = color == Piece.Color.White ? getWhitePieces() : getBlackPieces();

        for (int i = 0; i < currentPieces.size(); i++) {
            if (currentPieces.get(i).isType(type)) {
                if (off <= 0)
                    return currentPieces.get(i);
                else
                    off--;
            }
        }
        return null;
    }

    public static Piece get(final Piece.Color color, final Piece.Type type) {
        return get(color, type, 0);
    }

    public static util.Array<Piece> getWhitePieces() {
        return whitePieces;
    }

    public static util.Array<Piece> getBlackPieces() {
        return blackPieces;
    }

    public static util.Array<Piece> getAllPieces() {
        return allPieces;
    }

    public static boolean isNull(final int x, final int y) {
        return get(x, y) == null;
    }

    public static boolean isNull(final Vector position) {
        return isNull(position.X, position.Y);
    }

    public static boolean inPath(final int x, final int y, final int dx, final int dy, int ddx, int ddy) {
        Vector i = new Vector(x, y);
        i.add(ddx, ddy);

        while (i.X != dx || i.Y != dy) {
            if (!isNull(i))
                return false;

            i.add(ddx, ddy);
        }

        return true;
    }

    public static boolean inPath(final Vector from, final int dx, final int dy, int ddx, int ddy) {
        return inPath(from.X, from.Y, dx, dy, ddx, ddy);
    }

    public static boolean inPath(final Vector from, final Vector to, int ddx, int ddy) {
        return inPath(from, to.X, to.Y, ddx, ddy);
    }

    public static boolean inPath(final Vector from, final Vector to, Vector step) {
        return inPath(from, to, step.X, step.Y);
    }

    public static util.events.ArgEvent<Piece> onPieceEaten = new ArgEvent<>();
    public static util.events.ArgEvent<Move> onPieceMove = new ArgEvent<>();
    public static util.events.ArgEvent<Piece> onPieceMoved = new ArgEvent<>();

    public static void move(final Vector from,final Vector to) {
        onPieceMove.run(new Move(from, to));

        Piece temp = get(from);

        if (!isNull(to))
            onPieceEaten(get(to));

        pieces[from.X][from.Y] = null;
        pieces[to.X][to.Y] = temp;

        temp.updatePosition(to);
        onPieceMoved.run(temp);
    }

    public static void networkMove(final Vector from,final Vector to) {
        Move move = new Move(from, to);

        //Send move over network
        LocalClient.instance.send(new Packet(move.pack()));

        move(move);
    }

    public static void move(final Move move) {
        move(move.getFrom(), move.getTo());
    }

    public static void networkMove(final Move move) {
        networkMove(move.getFrom(), move.getTo());
    }

    public static boolean tryMove(final Vector from,final Vector to) {
        Piece temp = get(from);

        if (temp.canMove(to)) {
            networkMove(from, to);
            return true;
        }
        return false;
    }
    public static boolean tryMove(final Move move) {
        return tryMove(move.getFrom(), move.getTo());
    }
}