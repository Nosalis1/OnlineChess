package networking.packageSystem;

/**
 * Represents a packet used in the socket communication.
 */
@SuppressWarnings("unused")
public class Packet {
    private final String buffer;

    /**
     * Constructs a packet with the provided buffer.
     *
     * @param buffer The buffer of the packet
     */
    public Packet(String buffer) {
        this.buffer = buffer;
    }

    /**
     * Constructs a packet with the specified packet type.
     *
     * @param type The packet type
     */
    public Packet(PacketType type) {
        this.buffer = type.getCode();
    }

    /**
     * Constructs a packet with the specified packet type and buffer.
     *
     * @param type   The packet type
     * @param buffer The buffer of the packet
     */
    public Packet(PacketType type, String buffer) {
        this.buffer = type.getCode() + buffer;
    }

    /**
     * Returns the buffer data of the packet without the packet type code.
     *
     * @return The buffer data of the packet
     */
    public String getBufferData() {
        return this.buffer.substring(PacketType.CODE_LENGTH);
    }

    /**
     * Returns the packed buffer of the packet, including the packet type code.
     *
     * @return The packed buffer of the packet
     */
    public String getPackedBuffer() {
        return this.buffer;
    }

    /**
     * Returns the packet type of the packet.
     *
     * @return The packet type of the packet
     */
    public PacketType getType() {
        return PacketType.fromCode(this.buffer);
    }

    /**
     * Checks if the current packet is equal to another packet.
     *
     * @param other The other packet to compare
     * @return true if the packets are equal, false otherwise
     */
    public boolean equals(Packet other) {
        return getType() == other.getType();
    }

    /**
     * Returns a string representation of the packet.
     *
     * @return The string representation of the packet
     */
    @Override
    public String toString() {
        return "Packet{" +
                "buffer='" + buffer + '\'' +
                '}';
    }

    // Define constant packets
    public static final Packet START_GAME = new Packet(PacketType.START_GAME);
    public static final Packet CHANGE_COLOR = new Packet(PacketType.CHANGE_COLOR);
    public static final Packet DISCONNECT = new Packet(PacketType.DISCONNECT);
    public static final Packet SEND_PLAYER = new Packet(PacketType.SEND_PLAYER);
}