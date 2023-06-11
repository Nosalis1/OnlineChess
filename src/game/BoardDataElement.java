package game;

import utility.math.Array;

/**
 * Represents the data element of a chessboard.
 */
public class BoardDataElement {
    /**
     * The maximum number of pieces for each type.
     */
    @SuppressWarnings("unused")
    public static final int[] MAX_PIECES = {
            2, 2, 2, 1, 1, 8
    };

    /**
     * The scores assigned to each piece type.
     */
    public static final int[] PIECE_SCORES = {
            5, 3, 3, 9, 0, 1
    };

    private final Array<Piece> pieces = new Array<>();

    /**
     * Returns the array of pieces.
     *
     * @return The array of pieces.
     */
    public final Array<Piece> getPieces() {
        return this.pieces;
    }

    private final Array<Integer> piecesCount = new Array<>(6);

    /**
     * Returns the array of piece counts for each type.
     *
     * @return The array of piece counts.
     */
    public final Array<Integer> getPiecesCount() {
        return this.piecesCount;
    }

    private int score;

    /**
     * Returns the score associated with the board data element.
     *
     * @return The score.
     */
    public final int getScore() {
        return this.score;
    }

    private boolean inCheck = false;

    /**
     * Checks if the board data element is in a check state.
     *
     * @return True if in check, false otherwise.
     */
    public boolean isInCheck() {
        return this.inCheck;
    }

    /**
     * Sets the check state of the board data element.
     *
     * @param inCheck The check state to set.
     */
    public void setInCheck(final boolean inCheck) {
        this.inCheck = inCheck;
    }

    private boolean inCheckMate = false;

    /**
     * Checks if the board data element is in a checkmate state.
     *
     * @return True if in checkmate, false otherwise.
     */
    public boolean isInCheckMate() {
        return this.inCheckMate;
    }

    /**
     * Sets the checkmate state of the board data element.
     *
     * @param inCheckMate The checkmate state to set.
     */
    public void setInCheckMate(final boolean inCheckMate) {
        this.inCheckMate = inCheckMate;
    }

    /**
     * Resets the board data element.
     */
    public void reset() {
        pieces.clear();
        for (int i = 0; i < piecesCount.size(); i++)
            piecesCount.set(i, 0);

        score = 0;
    }

    /**
     * Adds a piece to the board data element.
     *
     * @param piece The piece to add.
     */
    public void addElement(final Piece piece) {
        pieces.add(piece);
        final int index = piece.getTypeCode() - 1;
        piecesCount.set(index, piecesCount.get(index) + 1);
        score += PIECE_SCORES[index];
    }

    /**
     * Handles the event when a piece is captured.
     *
     * @param piece The captured piece.
     */
    public void onPieceCaptured(Piece piece) {
        final int index = piece.getTypeCode() - 1;
        piecesCount.set(index, piecesCount.get(index) - 1);
        score -= PIECE_SCORES[index];

        if (pieces.contains(piece))
            pieces.remove(piece);
    }
}