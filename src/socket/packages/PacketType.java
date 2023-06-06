package socket.packages;

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
     * Get the code associated with the packet type.
     *
     * @return The code of the packet type
     */
    public final String getCode() {
        return this.code;
    }

    /**
     * Constructor for PacketType.
     *
     * @param code The code associated with the packet type
     */
    PacketType(final String code) {
        this.code = code;
    }

    /**
     * Get the PacketType based on the provided buffer code.
     *
     * @param buffer The buffer code to match with a PacketType
     * @return The corresponding PacketType
     * @throws IllegalArgumentException if the provided code is invalid
     */
    public static PacketType fromCode(String buffer) {
        final String bufferCode = buffer.substring(0, CODE_LENGTH);

        for (PacketType type : PacketType.values()) {
            if (type.code.equals(bufferCode)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + bufferCode);
    }
}