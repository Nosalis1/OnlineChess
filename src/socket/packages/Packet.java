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

    public void pack(Streamable streamable) {
        this.buffer = streamable.pack();
    }

    public void unapck(Streamable streamable) {
        streamable.unapck(this.buffer);
    }
}
