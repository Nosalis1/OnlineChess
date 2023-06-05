package game;

import util.Array;

public class BoardDataElement {
    @SuppressWarnings("unused")
    public static final int[] MAX_PIECES = {
            2, 2, 2, 1, 1, 8
    };
    public static final int[] PIECE_SCORES = {
            5, 3, 3, 9, 0, 1
    };

    private final util.Array<Piece> pieces = new Array<>();

    public final util.Array<Piece> getPieces() {
        return this.pieces;
    }

    private final util.Array<Integer> piecesCount = new Array<>(6);

    public final util.Array<Integer> getPiecesCount() {
        return this.piecesCount;
    }

    private int score;

    public final int getScore() {
        return this.score;
    }

    private boolean inCheck = false;

    public boolean isInCheck() {
        return this.inCheck;
    }

    public void setInCheck(final boolean inCheck) {
        this.inCheck = inCheck;
    }

    private boolean inCheckMate = false;

    public boolean isInCheckMate() {
        return this.inCheckMate;
    }

    public void setInCheckMate(final boolean inCheckMate) {
        this.inCheckMate = inCheckMate;
    }

    public void reset() {
        pieces.clear();
        for (int i = 0; i < piecesCount.size(); i++)
            piecesCount.set(i, 0);

        score = 0;
    }

    public void addElement(final Piece piece) {
        pieces.add(piece);
        final int index = piece.getTypeCode() - 1;
        piecesCount.set(index, piecesCount.get(index) + 1);
        score += PIECE_SCORES[index];
    }

    public void onPieceCaptured(Piece piece) {
        final int index = piece.getTypeCode() - 1;
        piecesCount.set(index, piecesCount.get(index) - 1);
        score -= PIECE_SCORES[index];

        if (pieces.contains(piece))
            pieces.remove(piece);
    }
}