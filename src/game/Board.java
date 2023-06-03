package game;

import socket.LocalClient;
import socket.packages.Packet;
import util.Array;
import util.Vector;
import util.events.ArgEvent;

public class Board {

    public static final Board instance = new Board();

    public static final int SIZE = 8;
    public static final int LAST = SIZE - 1;

    private Piece[][] pieces = new Piece[SIZE][SIZE];

    private Piece[][] getPiecesCopy() {
        Piece[][] copy = new Piece[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Piece piece = pieces[i][j];
                copy[i][j] = piece == null ? null : new Piece(
                        piece.getColor(), piece.getType(), new Vector(i, j)
                );
            }
        }
        return copy;
    }

    public Board(){
        this.data = new BoardData(this);
        this.data.updateData(this);
    }
    public Board(Board other) {
        this.pieces = other.getPiecesCopy();
        this.updatePieces();

        this.onPieceEaten.clear();
        this.onPieceMove.clear();
        this.onPieceMoved.clear();

        this.data = new BoardData(this);
        this.data.updateData(this);
    }

    private final util.Array<Piece> whitePieces = new Array<>();
    private final util.Array<Piece> blackPieces = new Array<>();
    private final util.Array<Piece> allPieces = new Array<>();

    public void updatePieces() {
        allPieces.clear();
        whitePieces.clear();
        blackPieces.clear();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (isNull(i, j))
                    continue;

                Piece tempPiece = pieces[i][j];
                allPieces.add(tempPiece);

                if (tempPiece.isColor(Piece.Color.White))
                    whitePieces.add(tempPiece);
                else
                    blackPieces.add(tempPiece);
            }
    }

    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pieces[i][j] = null;
            }
        }
    }

    private void addPiece(final Piece newPiece) {
        final Vector position = newPiece.getPosition();
        pieces[position.X][position.Y] = newPiece;

        allPieces.add(newPiece);

        if (newPiece.isColor(Piece.Color.White))
            whitePieces.add(newPiece);
        else
            blackPieces.add(newPiece);

    }

    public void reset() {
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

        this.data.updateData(this);
    }

    private void onPieceEaten(Piece piece) {
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

    public Piece get(final int x, final int y) {
        return pieces[x][y];
    }

    public Piece get(final Vector position) {
        return get(position.X, position.Y);
    }

    public Piece get(final Piece.Color color, final Piece.Type type, final int offset) {
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

    public Piece get(final Piece.Color color, final Piece.Type type) {
        return get(color, type, 0);
    }

    public util.Array<Piece> getWhitePieces() {
        return whitePieces;
    }

    public util.Array<Piece> getBlackPieces() {
        return blackPieces;
    }

    public util.Array<Piece> getAllPieces() {
        return allPieces;
    }

    public boolean isNull(final int x, final int y) {
        return get(x, y) == null;
    }

    public boolean isNull(final Vector position) {
        return isNull(position.X, position.Y);
    }

    public boolean inPath(final int x, final int y, final int dx, final int dy, int ddx, int ddy) {
        Vector i = new Vector(x, y);
        i.add(ddx, ddy);

        while (i.X != dx || i.Y != dy) {
            if (!isNull(i))
                return false;

            i.add(ddx, ddy);
        }

        return true;
    }

    public boolean inPath(final Vector from, final int dx, final int dy, int ddx, int ddy) {
        return inPath(from.X, from.Y, dx, dy, ddx, ddy);
    }

    public boolean inPath(final Vector from, final Vector to, int ddx, int ddy) {
        return inPath(from, to.X, to.Y, ddx, ddy);
    }

    @SuppressWarnings("unused")
    public boolean inPath(final Vector from, final Vector to, Vector step) {
        return inPath(from, to, step.X, step.Y);
    }

    public util.events.ArgEvent<Piece> onPieceEaten = new ArgEvent<>();
    public util.events.ArgEvent<Move> onPieceMove = new ArgEvent<>();
    public util.events.ArgEvent<Piece> onPieceMoved = new ArgEvent<>();

    private final BoardData data;
    public final BoardData getData() {
        return this.data;
    }

    public void move(final Vector from, final Vector to) {
        Move move = new Move(from, to);
        onPieceMove.run(move);

        Piece temp = get(from);

        if (!isNull(to))
            onPieceEaten(get(to));

        pieces[from.X][from.Y] = null;
        pieces[to.X][to.Y] = temp;

        temp.updatePosition(to);

        onPieceMoved.run(temp);
    }

    public void networkMove(final Vector from, final Vector to) {
        Move move = new Move(from, to);

        //Send move over network
        LocalClient.instance.send(new Packet(move.pack(Packet.Type.MOVE)));

        move(move);
    }

    public void move(final Move move) {
        move(move.getFrom(), move.getTo());
    }

    @SuppressWarnings("unused")
    public void networkMove(final Move move) {
        networkMove(move.getFrom(), move.getTo());
    }

    public boolean tryMove(final Vector from, final Vector to) {
        Piece temp = get(from);

        if (temp.canMove(to)) {
            networkMove(from, to);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public boolean tryMove(final Move move) {
        return tryMove(move.getFrom(), move.getTo());
    }

    public boolean isCheck(Piece.Color color) {
        Piece king = get(color, Piece.Type.King);
        if (king == null)
            return true;//TODO:ENDGAME

        util.Array<Piece> opponentPieces = color == Piece.Color.White ? blackPieces : whitePieces;
        for (int i = 0; i < opponentPieces.size(); i++) {
            if (opponentPieces.get(i).canMove(king.getPosition()))
                return true;
        }

        return false;
    }

    public boolean isCheckmate(Piece.Color color) {

        if (!isCheck(color))
            return false;

        util.Array<Piece> tempPieces = color == Piece.Color.White ? getWhitePieces() : getBlackPieces();
        util.Array<Move> legalMoves = new Array<>();
        tempPieces.foreach((Piece piece) -> {
            util.Array<Vector> moves = piece.getMoves();
            moves.foreach((Vector destination) -> legalMoves.add(new Move(piece.getPosition(), destination)));
        });

        for(int i =0;i<legalMoves.size();i++) {
            Move legalMove = legalMoves.get(i);
            Board tempBoard = new Board(this);
            tempBoard.move(legalMove);
            if (!tempBoard.isCheck(color))
                return false;
        }

        return true;
    }
}