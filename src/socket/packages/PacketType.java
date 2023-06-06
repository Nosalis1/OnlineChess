package socket.packages;

/**
 * Represents the types of packets used in the socket communication.
 */
public enum PacketType {
    START_GAME("$0000!"),
    CHANGE_COLOR("$0001!"),
    DISCONNECT("$0010!"),
    MOVE("$0011!"),
    CHANGE_TYPE("$0100!"),
    PLAYER("$0101!"),
    SEND_PLAYER("$0110!"),
    CUSTOM("$0111!");

    private final String code;
    public static final int CODE_LENGTH = 6;

    /**
     * Constructs a PacketType with the associated code.
     *
     * @param code The code associated with the packet type
     */
    PacketType(final String code) {
        this.code = code;
    }

    /**
     * Returns the code associated with the packet type.
     *
     * @return The code of the packet type
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the PacketType based on the provided buffer code.
     *
     * @param buffer The buffer code to match with a PacketType
     * @return The corresponding PacketType, or a default/unknown PacketType
     */
    public static PacketType fromCode(String buffer) {
        final String bufferCode = buffer.substring(0, CODE_LENGTH);

        for (PacketType type : PacketType.values()) {
            if (type.code.equals(bufferCode)) {
                return type;
            }
        }
        // Return a default/unknown packet type or handle the error appropriately
        return UNKNOWN;
    }

    // Define a default or unknown packet type
    public static final PacketType UNKNOWN = CUSTOM;
}