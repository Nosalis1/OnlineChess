package socket.exceptions;

/**
 * The NullPacketException is thrown when a null packet is encountered.
 */
public class NullPacketException extends Exception {
    /**
     * Constructs a NullPacketException with a default error message.
     */
    public NullPacketException() {
        super("Null packet encountered.");
    }
}