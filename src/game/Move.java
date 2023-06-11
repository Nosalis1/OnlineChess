package game;

import networking.packageSystem.Streamable;
import utility.math.Vector;

/**
 * Represents a move in the game.
 */
public class Move implements Streamable {

    private final Vector from;
    private final Vector to;

    /**
     * Initializes a new move with the specified source and destination vectors.
     *
     * @param from The source vector of the move.
     * @param to   The destination vector of the move.
     */
    public Move(Vector from, Vector to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the source vector of the move.
     *
     * @return The source vector.
     */
    public Vector getFrom() {
        return this.from;
    }

    /**
     * Returns the destination vector of the move.
     *
     * @return The destination vector.
     */
    public Vector getTo() {
        return this.to;
    }

    private final String STREAM_TOKEN = "~";

    @Override
    public String pack() {
        return from.pack() + STREAM_TOKEN + to.pack();
    }

    @Override
    public void unpack(String buffer) {
        String[] values = buffer.split(STREAM_TOKEN);

        if (values.length == 2) {
            try {
                this.from.unpack(values[0]);
                this.to.unpack(values[1]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("Invalid buffer format");
        }
    }
}