package socket.packages;

public class Packet {
    private final String buffer;

    /**
     * Get the buffer data of the packet without the packet type code.
     *
     * @return The buffer data of the packet
     */
    public String getBuffer() {
        return this.buffer.substring(PacketType.CODE_LENGTH);
    }

    /**
     * Get the packed buffer of the packet, including the packet type code.
     *
     * @return The packed buffer of the packet
     */
    public String getPackedBuffer() {
        return this.buffer;
    }

    /**
     * Get the packet type of the packet.
     *
     * @return The packet type of the packet
     */
    public PacketType getType() {
        return PacketType.fromCode(this.buffer);
    }

    /**
     * Create a packet with the provided buffer.
     *
     * @param buffer The buffer of the packet
     */
    public Packet(String buffer) {
        this.buffer = buffer;
    }

    /**
     * Create a packet with the specified packet type.
     *
     * @param type The packet type
     */
    public Packet(PacketType type) {
        this.buffer = type.getCode();
    }

    /**
     * Create a packet with the specified packet type and buffer.
     *
     * @param type   The packet type
     * @param buffer The buffer of the packet
     */
    public Packet(PacketType type, String buffer) {
        this.buffer = type.getCode() + buffer;
    }

    /**
     * Check if the current packet is equal to another packet.
     *
     * @param other The other packet to compare
     * @return true if the packets are equal, false otherwise
     */
    @SuppressWarnings("unused")
    public boolean equals(Packet other) {
        return getType() == other.getType();
    }

    public static final Packet START_GAME = new Packet(PacketType.START_GAME);
    public static final Packet CHANGE_COLOR = new Packet(PacketType.CHANGE_COLOR);
    public static final Packet DISCONNECT = new Packet(PacketType.DISCONNECT);
    public static final Packet SEND_PLAYER = new Packet(PacketType.SEND_PLAYER);
}