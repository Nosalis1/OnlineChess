package socket.exceptions;

public class InvalidBufferFormatException extends Exception {
    public InvalidBufferFormatException(final String message) {
        super(message);
    }

    public InvalidBufferFormatException(final String message, Exception ex) {
        super(message);
        ex.printStackTrace();
    }
}