package socket.packages;

public class Packet {
    private final String buffer;

    public String getBuffer() {
        return this.buffer.substring(PacketType.CODE_LENGTH);
    }

    public String getPackedBuffer() {
        return this.buffer;
    }

    public PacketType getType() {
        return PacketType.fromCode(this.buffer);
    }

    public Packet(String buffer) {
        this.buffer = buffer;
    }

    public Packet(PacketType type) {
        this.buffer = type.getCode();
    }

    public Packet(PacketType type, String buffer) {
        this.buffer = type.getCode() + buffer;
    }

    @SuppressWarnings("unused")
    public boolean equals(Packet other) {
        return getType() == other.getType();
    }
}