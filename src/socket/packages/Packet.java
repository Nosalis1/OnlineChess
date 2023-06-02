package socket.packages;

public class Packet {
    public enum Type {
        START_GAME("$0000!"),
        CHANGE_COLOR("$0001!"),
        DISCONNECT("$0010!"),
        MOVE("$0011!"),
        CUSTOM("$0100!");

        private final String code;
        public static final int CODE_LENGTH = 6;

        public final String getCode() {
            return this.code;
        }

        Type(final String code) {
            this.code = code;
        }

        public static Type fromCode(String buffer) {
            final String bufferCode = buffer.substring(0, CODE_LENGTH);

            for (Type type : Type.values()) {
                if (type.code.equals(bufferCode)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + bufferCode);
        }
    }

    private String buffer;

    public String getBuffer() {
        return this.buffer.substring(Type.CODE_LENGTH);
    }

    public String getPackedBuffer() {
        return this.buffer;
    }

    public Type getType() {
        return Type.fromCode(this.buffer);
    }

    public void setBuffer(String buffer, Type type) {
        this.buffer = type.getCode() + buffer;
    }

    public void setReceivedBuffer(String buffer) {
        this.buffer = buffer;
    }

    public Packet(String buffer) {
        this.buffer = buffer;
    }
    public Packet(String buffer, Type type) {
        setBuffer(buffer, type);
    }

    @SuppressWarnings("unused")
    public boolean equals(Packet other) {
        return getType() == other.getType();
    }

    @SuppressWarnings("unused")
    public void pack(Streamable streamable, Type type) {
        this.buffer = streamable.pack(type);
    }

    @SuppressWarnings("unused")
    public void unapck(Streamable streamable) {
        streamable.unapck(getBuffer());
    }
}