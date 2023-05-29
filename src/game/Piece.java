package game;

import util.Array;
import util.Vector;

public class Piece {

    public enum Color {
        White(0),
        Black(1);

        private final int code;

        public final int getCode() {
            return this.code;
        }

        Color(final int code) {
            this.code = code;
        }
    }

    private final Color color;

    public final Color getColor() {
        return this.color;
    }

    public final int getColorCode() {
        return this.color.getCode();
    }

    public final boolean isColor(final Color color) {
        return this.color == color;
    }

    public enum Type {
        Rook(1),
        Knight(2),
        Bishop(3),
        Queen(4),
        King(5),
        Pawn(6);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }

        public static Type fromCode(int code) {
            for (Type type : Type.values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

    private final Type type;

    public final Type getType() {
        return this.type;
    }

    public final int getTypeCode() {
        return this.type.code;
    }

    public final boolean isType(final Type type) {
        return this.type == type;
    }

    public final boolean isPiece(final Color color, final Type type) {
        return isColor(color) && isType(type);
    }

    private Vector position = null;

    public final Vector getPosition() {
        return this.position;
    }

    public void setPosition(Vector position) {
        if (!position.inBounds(-1, 8))
            return;
        this.position = position;
    }

    public void updatePosition(int x, int y) {
        this.position.X = x;
        this.position.Y = y;
    }

    public void updatePosition(final Vector newPosition) {
        updatePosition(newPosition.X, newPosition.Y);
    }

    private final util.Array<Vector> moves = new Array<>();

    public util.Array<Vector> getMoves() {
        updateMoves();
        return this.moves;
    }

    private void tryAddMove(int x, int y) {
        if (canMove(x, y))
            moves.add(new Vector(x, y));
    }

    public void updateMoves() {
        moves.clear();

        final int code = getTypeCode();

        switch (code) {
            case 6 -> getPawnMoves();
            case 5 -> getKingMoves();
            case 4 -> getQueenMoves();
            case 3 -> getBishopMoves();
            case 2 -> getKnightMoves();
            case 1 -> getRookMoves();
            default -> throw new IndexOutOfBoundsException(code);
        }
    }

    private void getRookMoves() {
        int x, y;
        for (int i = 0; i < 8; i++) {
            x = position.X;
            y = i;

            tryAddMove(x, y);

            x = i;
            y = position.Y;

            tryAddMove(x, y);
        }
    }

    private void getKnightMoves() {
        int x, y;

        //SIDE A
        x = position.X - 1;
        y = position.Y - 2;

        tryAddMove(x, y);

        x = position.X - 1;
        y = position.Y + 2;

        tryAddMove(x, y);

        x = position.X + 1;
        y = position.Y - 2;

        tryAddMove(x, y);

        x = position.X + 1;
        y = position.Y + 2;

        tryAddMove(x, y);
        //SIDE B
        x = position.X - 2;
        y = position.Y - 1;

        tryAddMove(x, y);

        x = position.X - 2;
        y = position.Y + 1;

        tryAddMove(x, y);

        x = position.X + 2;
        y = position.Y - 1;

        tryAddMove(x, y);

        x = position.X + 2;
        y = position.Y + 1;

        tryAddMove(x, y);
    }

    private void getBishopMoves() {
        int x, y;
        for (int i = -8; i < 8; i++) {

            if (i == 0)
                continue;

            x = position.X + i;
            y = position.Y + i;

            tryAddMove(x, y);

            y = position.Y - i;
            tryAddMove(x, y);
        }
    }

    private void getQueenMoves() {
        getKingMoves();
        getBishopMoves();
        getRookMoves();
    }

    private void getKingMoves() {
        int x, y;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if (i == 0 && j == 0)
                    continue;

                x = position.X + i;
                y = position.Y + j;

                tryAddMove(x, y);
            }
        }
    }

    private void getPawnMoves() {
        int direction = isColor(Color.White) ? 1 : -1;

        int x, y;

        x = position.X + direction * 2;
        y = position.Y;
        tryAddMove(x, y);

        x = position.X + direction;
        tryAddMove(x, y);

        y = position.Y + 1;
        tryAddMove(x, y);

        y = position.Y - 1;
        tryAddMove(x, y);
    }

    public Piece(final Color color, final Type type, final Vector position) {
        this.color = color;
        this.type = type;
        setPosition(position);
    }

    public boolean canMove(final Vector destination) {
        return canMove(destination.X, destination.Y);
    }

    public boolean canMove(final int x, final int y) {

        if ((x <= -1 || x >= 8) || (y <= -1 || y >= 8))
            return false;

        if (!Board.isNull(x, y) && isColor(Board.get(x, y).getColor()))
            return false;

        final int code = getTypeCode();

        return switch (code) {
            case 6 -> canPawnMove(x, y);
            case 5 -> canKingMove(x, y);
            case 4 -> canQueenMove(x, y);
            case 3 -> canBishopMove(x, y);
            case 2 -> canKnightMove(x, y);
            case 1 -> canRookMove(x, y);
            default -> throw new IndexOutOfBoundsException(code);
        };
    }

    private boolean canRookMove(int x, int y) {
        if (position.X != x && position.Y != y)
            return false;

        int ddx = (position.X == x) ? 0
                : (x - position.X) / Math.abs(x - position.X);
        int ddy = (position.Y == y) ? 0
                : (y - position.Y) / Math.abs(y - position.Y);

        return Board.inPath(position, x, y, ddx, ddy);
    }

    private boolean canKnightMove(int x, int y) {
        int ddx = Math.abs(x - position.X);
        int ddy = Math.abs(y - position.Y);

        return (ddx == 2 && ddy == 1) || (ddx == 1 && ddy == 2);
    }

    private boolean canBishopMove(int x, int y) {
        int ddx = Math.abs(x - position.X);
        int ddy = Math.abs(y - position.Y);

        if (ddx != ddy)
            return false;

        ddx = Integer.signum(x - position.X);
        ddy = Integer.signum(y - position.Y);

        return Board.inPath(position, x, y, ddx, ddy);
    }

    private boolean canQueenMove(int x, int y) {
        int ddx = Math.abs(x - position.X);
        int ddy = Math.abs(y - position.Y);

        if (ddx != ddy && ddx != 0 && ddy != 0)
            return false;

        ddx = Integer.compare(x, position.X);
        ddy = Integer.compare(y, position.Y);

        return Board.inPath(position, x, y, ddx, ddy);
    }

    private boolean canKingMove(int x, int y) {
        int ddx = Math.abs(x - position.X);
        int ddy = Math.abs(y - position.Y);

        return ddx <= 1 && ddy <= 1;

    }

    private boolean canPawnMove(int x, int y) {
        int direction = isColor(Color.White) ? 1 : -1;

        if (y == position.Y && (x == position.X + direction
                || (position.X == 1 || position.X == 6)
                && x == position.X + (2 * direction))) {
            return Board.isNull(x, y);
        }

        if (Math.abs(y - position.Y) == 1 && x == position.X + direction) {
            return !Board.isNull(x, y) && !isColor(Board.get(x, y).getColor());
        }

        return false;
    }
}