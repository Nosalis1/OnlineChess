package game;

import socket.packages.Packet;
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
    public void unapck(String buffer) {
        String[] values = buffer.split(STREAM_TOKEN);

        if (values.length == 2) {
            this.from.unapck(values[0]);
            this.to.unapck(values[1]);
        } else
            throw new IllegalArgumentException("Invalid buffer format");
    }
}