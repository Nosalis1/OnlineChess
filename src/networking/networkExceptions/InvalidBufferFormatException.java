package networking.networkExceptions;

/**
 * The InvalidBufferFormatException is thrown when an invalid buffer format is encountered.
 */
public class InvalidBufferFormatException extends Exception {
    /**
     * Constructs an InvalidBufferFormatException with the specified error message.
     *
     * @param message The error message explaining the reason for the exception
     */
    public InvalidBufferFormatException(String message) {
        super(message);
    }

    /**
     * Constructs an InvalidBufferFormatException with the specified error message and the cause of the exception.
     *
     * @param message The error message explaining the reason for the exception
     * @param cause   The underlying cause of the exception
     */
    public InvalidBufferFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}