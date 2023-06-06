package game;

import util.Array;
import util.Vector;

/**

 The Piece class represents a chess piece with a color and a type.

 It also manages the piece's position on the chessboard and provides methods for updating the position and calculating valid moves.
 */
public class Piece {

    /**
     * Enumeration for the color of the piece (White or Black).
     */
    public enum Color {
        White(0),
        Black(1);

        private final int code;

        /**
         * Constructs a Color enum with the given code.
         *
         * @param code the code representing the color
         */
        Color(final int code) {
            this.code = code;
        }

        /**
         * Returns the code representing the color.
         *
         * @return the code of the color
         */
        public final int getCode() {
            return this.code;
        }
    }

    private final Color color;

    /**
     * Retrieves the color of the piece.
     *
     * @return the color of the piece
     */
    public final Color getColor() {
        return this.color;
    }

    /**
     * Retrieves the code representing the color of the piece.
     *
     * @return the code of the color
     */
    public final int getColorCode() {
        return this.color.getCode();
    }

    /**
     * Checks if the piece has the specified color.
     *
     * @param color the color to check
     * @return true if the piece has the specified color, false otherwise
     */
    public final boolean isColor(final Color color) {
        return this.color == color;
    }

    /**
     * Enumeration for the type of the piece (Rook, Knight, Bishop, Queen, King, Pawn).
     */
    public enum Type {
        Rook(1),
        Knight(2),
        Bishop(3),
        Queen(4),
        King(5),
        Pawn(6);

        private final int code;

        /**
         * Constructs a Type enum with the given code.
         *
         * @param code the code representing the type
         */
        Type(int code) {
            this.code = code;
        }

        /**
         * Returns the code representing the type.
         *
         * @return the code of the type
         */
        @SuppressWarnings("unused")
        public int getCode() {
            return this.code;
        }

        /**
         * Returns the Type enum corresponding to the given code.
         *
         * @param code the code of the type
         * @return the Type enum corresponding to the code
         * @throws IllegalArgumentException if the code is invalid
         */
        public static Type fromCode(int code) {
            for (Type type : Type.values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

    private Type type;

    /**
     * Retrieves the type of the piece.
     *
     * @return the type of the piece
     */
    public final Type getType() {
        return this.type;
    }

    /**
     * Retrieves the code representing the type of the piece.
     *
     * @return the code of the type
     */
    public final int getTypeCode() {
        return this.type.code;
    }

    /**
     * Checks if the piece has the specified type.
     *
     * @param type the type to check
     * @return true if the piece has the specified type, false otherwise
     */
    public final boolean isType(final Type type) {
        return this.type == type;
    }

    /**
     * Change the type of piece.
     * @param toType type of piece we want to change to
     */
    public void promote(final Type toType) {
        this.type = toType;
    }

    /**
     * Checks if the piece has the specified color and type.
     *
     * @param color the color to check
     * @param type  the type to check
     * @return true if the piece has the specified color and type, false otherwise
     */
    @SuppressWarnings("unused")
    public final boolean isPiece(final Color color, final Type type) {
        return isColor(color) && isType(type);
    }

    private Vector position = null;

    /**
     * Retrieves the position of the piece on the chessboard.
     *
     * @return the position of the piece
     */
    public final Vector getPosition() {
        return this.position;
    }

    /**
     * Sets the position of the piece on the chessboard.
     *
     * @param position the new position of the piece
     */
    public void setPosition(Vector position) {
        if (!position.inBounds(-1, 8))
            return;
        this.position = position;
    }

    /**
     * Updates the position of the piece on the chessboard using the given coordinates.
     *
     * @param x the x-coordinate of the new position
     * @param y the y-coordinate of the new position
     */
    public void updatePosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
    }

    /**
     * Updates the position of the piece on the chessboard using the given vector.
     *
     * @param newPosition the new position as a vector
     */
    public void updatePosition(final Vector newPosition) {
        updatePosition(newPosition.x, newPosition.y);
    }

    private final util.Array<Vector> moves = new Array<>();

    /**
     * Retrieves the valid moves for the piece.
     *
     * @return an array of valid moves
     */
    public util.Array<Vector> getMoves() {
        updateMoves();
        return this.moves;
    }

    private void tryAddMove(int x, int y) {
        if (canMove(x, y))
            moves.add(new Vector(x, y));
    }

    /**
     * Updates the valid moves for the piece based on its current position and type.
     */
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
            x = position.x;
            y = i;

            tryAddMove(x, y);

            x = i;
            y = position.y;

            tryAddMove(x, y);
        }
    }

    private void getKnightMoves() {
        int x, y;

        //SIDE A
        x = position.x - 1;
        y = position.y - 2;

        tryAddMove(x, y);

        x = position.x - 1;
        y = position.y + 2;

        tryAddMove(x, y);

        x = position.x + 1;
        y = position.y - 2;

        tryAddMove(x, y);

        x = position.x + 1;
        y = position.y + 2;

        tryAddMove(x, y);
        //SIDE B
        x = position.x - 2;
        y = position.y - 1;

        tryAddMove(x, y);

        x = position.x - 2;
        y = position.y + 1;

        tryAddMove(x, y);

        x = position.x + 2;
        y = position.y - 1;

        tryAddMove(x, y);

        x = position.x + 2;
        y = position.y + 1;

        tryAddMove(x, y);
    }

    private void getBishopMoves() {
        int x, y;
        for (int i = -8; i < 8; i++) {

            if (i == 0)
                continue;

            x = position.x + i;
            y = position.y + i;

            tryAddMove(x, y);

            y = position.y - i;
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

                x = position.x + i;
                y = position.y + j;

                tryAddMove(x, y);
            }
        }
    }

    private void getPawnMoves() {
        int direction = isColor(Color.White) ? 1 : -1;

        int x, y;

        x = position.x + direction * 2;
        y = position.y;
        tryAddMove(x, y);

        x = position.x + direction;
        tryAddMove(x, y);

        y = position.y + 1;
        tryAddMove(x, y);

        y = position.y - 1;
        tryAddMove(x, y);
    }

    /**
     * Creates a new instance of the Piece class with the specified color, type, and position.
     *
     * @param color    the color of the piece
     * @param type     the type of the piece
     * @param position the position of the piece on the chessboard
     */
    public Piece(final Color color, final Type type, final Vector position) {
        this.color = color;
        this.type = type;
        setPosition(position);
    }

    /**
     * Checks if the piece can move to the specified destination.
     *
     * @param destination the destination position
     * @return true if the move is valid, false otherwise
     */
    public boolean canMove(final Vector destination) {
        return canMove(destination.x, destination.y);
    }

    /**
     * Checks if the piece can move to the specified coordinates.
     *
     * @param x the x-coordinate of the destination
     * @param y the y-coordinate of the destination
     * @return true if the move is valid, false otherwise
     */
    public boolean canMove(final int x, final int y) {

        if ((x <= -1 || x >= 8) || (y <= -1 || y >= 8))
            return false;

        if (!Board.instance.isNull(x, y) && isColor(Board.instance.get(x, y).getColor()))
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
        if (position.x != x && position.y != y)
            return false;

        int ddx = (position.x == x) ? 0
                : (x - position.x) / Math.abs(x - position.x);
        int ddy = (position.y == y) ? 0
                : (y - position.y) / Math.abs(y - position.y);

        return Board.instance.inPath(position, x, y, ddx, ddy);
    }

    private boolean canKnightMove(int x, int y) {
        int ddx = Math.abs(x - position.x);
        int ddy = Math.abs(y - position.y);

        return (ddx == 2 && ddy == 1) || (ddx == 1 && ddy == 2);
    }

    private boolean canBishopMove(int x, int y) {
        int ddx = Math.abs(x - position.x);
        int ddy = Math.abs(y - position.y);

        if (ddx != ddy)
            return false;

        ddx = Integer.signum(x - position.x);
        ddy = Integer.signum(y - position.y);

        return Board.instance.inPath(position, x, y, ddx, ddy);
    }

    private boolean canQueenMove(int x, int y) {
        int ddx = Math.abs(x - position.x);
        int ddy = Math.abs(y - position.y);

        if (ddx != ddy && ddx != 0 && ddy != 0)
            return false;

        ddx = Integer.compare(x, position.x);
        ddy = Integer.compare(y, position.y);

        return Board.instance.inPath(position, x, y, ddx, ddy);
    }

    private boolean canKingMove(int x, int y) {
        int ddx = Math.abs(x - position.x);
        int ddy = Math.abs(y - position.y);

        return ddx <= 1 && ddy <= 1;

    }

    private boolean canPawnMove(int x, int y) {
        int direction = isColor(Color.White) ? 1 : -1;

        if (y == position.y && (x == position.x + direction
                || (position.x == 1 || position.x == 6)
                && x == position.x + (2 * direction))) {
            return Board.instance.isNull(x, y);
        }

        if (Math.abs(y - position.y) == 1 && x == position.x + direction) {
            return !Board.instance.isNull(x, y) && !isColor(Board.instance.get(x, y).getColor());
        }

        return false;
    }
}