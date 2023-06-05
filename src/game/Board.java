package game;

import socket.LocalClient;
import socket.packages.Packet;
import util.Array;
import util.Vector;
import util.events.ArgEvent;
import util.events.Event;

import java.nio.IntBuffer;
import java.time.Period;

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

    public Board() {
        this.data = new BoardData(this);
        this.data.updateElements();
    }

    public Board(Board other) {
        this.pieces = other.getPiecesCopy();

        this.data = new BoardData(this);
        this.data.updateElements();

        this.onMove.clear();
        this.onMoved.clear();
        this.onMoveDone.clear();

        this.onCapture.clear();
        this.onCaptured.clear();

        this.onCheck.clear();
        this.onCheckMate.clear();

        this.onPromotion.clear();
        this.onPiecePromoted.clear();
        this.onPiecePromotion.clear();

        this.whiteTurn = other.whiteTurn;
    }

    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pieces[i][j] = null;
            }
        }
        this.data.resetElements();
    }

    private void addPiece(final Piece newPiece) {
        final Vector position = newPiece.getPosition();
        pieces[position.X][position.Y] = newPiece;
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

        this.data.updateElements();
    }

    public Piece get(final int x, final int y) {
        return pieces[x][y];
    }

    public Piece get(final Vector position) {
        return get(position.X, position.Y);
    }

    public Piece get(final Piece.Color color, final Piece.Type type, final int offset) {
        int off = offset;

        util.Array<Piece> currentPieces = color == Piece.Color.White ? data.white.getPieces() : data.black.getPieces();

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

    private boolean whiteTurn = true;

    public final boolean isWhiteTurn() {
        return this.whiteTurn;
    }

    private void skipTurn() {
        this.whiteTurn = !this.whiteTurn;
    }

    private final BoardData data;

    public final BoardData getData() {
        return this.data;
    }

    public util.events.Event onCapture = new Event();
    public util.events.ArgEvent<Piece> onCaptured = new ArgEvent<>();
    public util.events.Event onMove = new Event();
    public util.events.ArgEvent<Piece> onMoved = new ArgEvent<>();
    public util.events.ArgEvent<Move> onMoveDone = new ArgEvent<>();

    public util.events.ArgEvent<Piece.Color> onCheck = new ArgEvent<>();
    public util.events.ArgEvent<Piece.Color> onCheckMate = new ArgEvent<>();

    public util.events.Event onPromotion = new Event();
    public util.events.ArgEvent<Piece> onPiecePromotion = new ArgEvent<>();
    public util.events.ArgEvent<Piece> onPiecePromoted = new ArgEvent<>();

    public void move(final Vector from, final Vector to) {
        onMove.run();

        Piece temp = get(from);

        if (!isNull(to)) {
            onCapture.run();
            onCaptured.run(this.get(to));
        }

        pieces[from.X][from.Y] = null;
        pieces[to.X][to.Y] = temp;

        temp.updatePosition(to);
        onMoved.run(temp);

        onMoveDone.run(new Move(from, to));

        if (!checkPromotion(temp))
            skipTurn();

        checkCheckMate();
    }

    public void move(final Move move) {
        move(move.getFrom(), move.getTo());
    }

    public void networkMove(final Vector from, final Vector to) {
        Move move = new Move(from, to);

        //Send move over network
        LocalClient.instance.send(new Packet(move.pack(Packet.Type.MOVE)));

        move(move);
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

    private void checkCheck() {
        Piece king = get(Piece.Color.White, Piece.Type.King);
        data.white.setInCheck(false);

        if (king == null)
            data.white.setInCheck(true);
        else {
            util.Array<Piece> opponentPieces = data.black.getPieces();
            for (int i = 0; i < opponentPieces.size(); i++)
                if (opponentPieces.get(i).canMove(king.getPosition())) {
                    data.white.setInCheck(true);
                    break;
                }
        }

        if (data.white.isInCheck())
            onCheck.run(Piece.Color.White);

        king = get(Piece.Color.Black, Piece.Type.King);
        data.black.setInCheck(false);

        if (king == null)
            data.black.setInCheck(true);
        else {
            util.Array<Piece> opponentPieces = data.white.getPieces();
            for (int i = 0; i < opponentPieces.size(); i++)
                if (opponentPieces.get(i).canMove(king.getPosition())) {
                    data.black.setInCheck(true);
                    break;
                }
        }

        if (data.black.isInCheck())
            onCheck.run(Piece.Color.Black);
    }

    private void checkCheckMate() {
        checkCheck();

        if (instance != this)
            return;

        util.Array<Piece> tempPieces;

        if (data.white.isInCheck()) {
            data.white.setInCheckMate(true);

            tempPieces = data.white.getPieces();
            util.Array<Move> legalMoves = new Array<>();
            tempPieces.foreach((Piece piece) -> {
                util.Array<Vector> moves = piece.getMoves();
                moves.foreach((Vector destination) -> legalMoves.add(new Move(piece.getPosition(), destination)));
            });

            for (int i = 0; i < legalMoves.size(); i++) {
                Move move = legalMoves.get(i);
                Board tempBoard = new Board(this);

                tempBoard.move(move);

                if (!tempBoard.data.white.isInCheck()) {
                    data.white.setInCheckMate(false);
                    break;
                }
            }

            if (data.white.isInCheckMate())
                onCheckMate.run(Piece.Color.White);
        }

        if (data.black.isInCheck()) {
            data.black.setInCheckMate(true);

            tempPieces = data.black.getPieces();
            util.Array<Move> legalMoves = new Array<>();
            tempPieces.foreach((Piece piece) -> {
                util.Array<Vector> moves = piece.getMoves();
                moves.foreach((Vector destination) -> legalMoves.add(new Move(piece.getPosition(), destination)));
            });

            for (int i = 0; i < legalMoves.size(); i++) {
                Move move = legalMoves.get(i);
                Board tempBoard = new Board(this);

                tempBoard.move(move);

                if (!tempBoard.data.black.isInCheck()) {
                    data.black.setInCheckMate(false);
                    break;
                }
            }

            if (data.black.isInCheckMate())
                onCheckMate.run(Piece.Color.Black);
        }
    }

    private boolean checkPromotion(Piece piece) {
        if (piece == null || !piece.isType(Piece.Type.Pawn))
            return false;

        final int x = piece.getPosition().X;
        if (x == 0 || x == LAST) {
            onPromotion.run();
            onPiecePromotion.run(piece);
            return true;
        }

        return false;
    }

    private void promotePiece(Piece piece, Piece.Type toType) {
        piece.promote(toType);
        onPiecePromoted.run(piece);
        skipTurn();
    }

    public void networkChangePiece(Piece current, final Piece.Type toType) {
        Vector position = current.getPosition();
        String buffer = position.pack(null);
        buffer += "~" + Integer.toString(toType.getCode());

        //Send change over network
        LocalClient.instance.send(new Packet(buffer, Packet.Type.CHANGE_TYPE));
        System.out.println("CHANGE TYPE SENT");
        promotePiece(current,toType);
    }
}