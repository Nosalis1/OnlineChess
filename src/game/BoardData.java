package game;

import utility.math.Array;
import utility.math.Vector;

/**
 * Represents the data of the game board.
 */
public class BoardData {

    private final Board board;

    /**
     * Constructs a new BoardData instance.
     *
     * @param board The associated game board.
     */
    public BoardData(Board board) {
        this.board = board;

        // Add event listeners
        this.board.onMoveDone.add(this::addMove);
        this.board.onCaptured.add((Piece piece) -> {
            if (piece.isColor(Piece.Color.White))
                white.onPieceCaptured(piece);
            else
                black.onPieceCaptured(piece);

            if (allPieces.contains(piece))
                allPieces.remove(piece);
        });
    }

    private final Array<Piece> allPieces = new Array<>();
    public final Array<Piece> getAllPieces() {
        return this.allPieces;
    }

    public final BoardDataElement white = new BoardDataElement();
    public final BoardDataElement black = new BoardDataElement();

    /**
     * Resets the board data elements.
     */
    public void resetElements() {
        allPieces.clear();
        white.reset();
        black.reset();
    }

    /**
     * Updates the board data elements based on the current state of the game board.
     */
    public void updateElements() {
        resetElements();

        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.isNull(i, j))
                    continue;

                Piece temp = board.get(i, j);

                allPieces.add(temp);
                if (temp.isColor(Piece.Color.White))
                    white.addElement(temp);
                else
                    black.addElement(temp);
            }
        }
    }

    private final String[] LABELS = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private final Array<String> moves = new Array<>();

    /**
     * Returns the recorded moves in algebraic notation.
     *
     * @return The recorded moves.
     */
    public final Array<String> getMoves() {
        return this.moves;
    }

    private void addMove(final Move move) {
        moves.add(
                (moves.size() + 1) +
                        " - " +
                        convertVector(move.getFrom()) +
                        " : " +
                        convertVector(move.getTo())
        );
    }

    private String convertVector(final Vector vector) {
        return (vector.x + 1) + LABELS[vector.y];
    }
}