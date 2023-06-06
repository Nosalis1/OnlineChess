package game;

import socket.packages.Streamable;
import util.Vector;

public class Move implements Streamable {

    private final Vector from;
    private final Vector to;

    public Vector getFrom() {
        return this.from;
    }

    public Vector getTo() {
        return this.to;
    }

    public Move(Vector from, Vector to) {
        this.from = from;
        this.to = to;
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
            }catch (Exception ex){
                ex.printStackTrace();
            }
        } else
            throw new IllegalArgumentException("Invalid buffer format");
    }
}