package game;

import util.Array;
import util.Vector;

public class BoardData {

    public BoardData(Board board) {
        board.onPieceMove.add(this::onPieceMove);
        board.onPieceEaten.add(this::onPieceEaten);
    }

    private final util.Array<Integer> whiteData = new Array<>(6);

    public final util.Array<Integer> getWhiteData() {
        return this.whiteData;
    }

    private final util.Array<Integer> blackData = new Array<>(6);

    public final util.Array<Integer> getBlackData() {
        return this.blackData;
    }

    public void updateData(Board board) {
        for (int i = 0; i < whiteData.size(); i++) {
            whiteData.set(i, 0);
            blackData.set(i, 0);
        }

        util.Array<Piece> tempPieces = board.getWhitePieces();
        int index;
        for (int i = 0; i < tempPieces.size(); i++) {
            index = tempPieces.get(i).getTypeCode() - 1;
            whiteData.set(index, whiteData.get(index) + 1);
        }

        tempPieces = board.getBlackPieces();
        for (int i = 0; i < tempPieces.size(); i++) {
            index = tempPieces.get(i).getTypeCode() - 1;
            blackData.set(index, blackData.get(index) + 1);
        }

        this.updatePieceScore();
    }

    public final int[] MAX_PIECES = {
            2, 2, 2, 1, 1, 8
    };

    public final int getMissingPieces(final Piece.Color color,final Piece.Type type) {
        util.Array<Integer> tempData = color == Piece.Color.White ? whiteData : blackData;
        final int CODE = type.getCode() - 1;
        return MAX_PIECES[CODE] - tempData.get(CODE);
    }

    public final int[] PIECE_SCORES = {
            5, 3, 3, 9, 0, 1
    };

    private int whitePieceScore, blackPieceScore;

    public final int getWhitePieceScore() {
        return this.whitePieceScore;
    }

    public final int getBlackPieceScore() {
        return this.blackPieceScore;
    }

    private void updatePieceScore() {
        this.whitePieceScore = 0;
        this.blackPieceScore = 0;
        for (int i = 0; i < whiteData.size(); i++) {
            this.whitePieceScore += this.whiteData.get(i) * PIECE_SCORES[i];
            this.blackPieceScore += this.blackData.get(i) * PIECE_SCORES[i];
        }
    }

    private final util.Array<String> moves = new Array<>();

    public final util.Array<String> getMoves() {
        return this.moves;
    }

    private final String[] LABELS = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private String convertVector(final Vector vector) {
        return (vector.X + 1) + LABELS[vector.Y];
    }

    private String convertMove(final Move move) {
        return (moves.size() + 1) + " - " + convertVector(move.getFrom()) + " : " + convertVector(move.getTo());
    }

    public void onPieceMove(Move move) {
        moves.add(convertMove(move));
    }

    public void onPieceEaten(Piece piece) {
        if (piece == null)
            return;

        util.Array<Integer> tempData;
        final int index = piece.getTypeCode() - 1;

        if (piece.isColor(Piece.Color.White)) {
            tempData = whiteData;
            whitePieceScore -= PIECE_SCORES[index];
        } else {
            tempData = blackData;
            blackPieceScore -= PIECE_SCORES[index];
        }
        tempData.set(index, tempData.get(index) - 1);
    }
}