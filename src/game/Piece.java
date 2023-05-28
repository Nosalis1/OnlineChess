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

        updateMoves();
    }

    public void updatePosition(int x, int y) {
        this.position.X = x;
        this.position.Y = y;

        updateMoves();
    }

    private util.Array<Vector> moves = new Array<>();

    public util.Array<Vector> getMoves() {
        return this.moves;
    }

    public void updateMoves() {
        moves.clear();

        //TODO: UPDATE MOVES
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

        //TODO: CHECK IF DESTINATION IS NULL && COLOR IS SAME => FALSE

        final int code = getTypeCode();

        switch (code) {
            case 6:
                return canPawnMove(x, y);
            case 5:
            case 4:
            case 3:
            case 2:
            case 1:
        }
        return false;
    }

    private boolean canPawnMove(int x,int y) {
return false;
    }
}
