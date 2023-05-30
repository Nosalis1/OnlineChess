package socket.packages;

public class Packet {
    private String buffer;

    public String getBuffer() {
        return this.buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public Packet(String buffer) {
        this.buffer = buffer;
    }

    public boolean equals(Packet other) {
        return this.buffer.equals(other.getBuffer());
    }

    public void pack(Streamable streamable) {
        this.buffer = streamable.pack();
    }

    public void unapck(Streamable streamable) {
        streamable.unapck(this.buffer);
    }

    public static final Packet DISCONNECTED = new Packet("DISCONNECTED");
    public static final Packet START_GAME = new Packet("START_GAME");
    public static final Packet CHANGE_COLOR = new Packet("CHANGE_COLOR");
}
