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
    public String pack(Packet.Type type) {
        return type.getCode() + from.pack(type) + STREAM_TOKEN + to.pack(type);
    }

    @Override
    public void unapck(String buffer) {
        String[] values = buffer.split(STREAM_TOKEN);
        for (String str : values)
            System.out.println(str);
        if (values.length == 2) {
            this.from.unapck(values[0]);
            System.out.println("CONVERTED FROM : " + this.from);
            this.to.unapck(values[1]);
            System.out.println("CONVERTED TO : " + this.to);
        } else
            throw new IllegalArgumentException("Invalid buffer format");

        System.out.println(this.from);
        System.out.println(this.to);
    }
}