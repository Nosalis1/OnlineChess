package networking.networkExceptions;

/**
 * The ClosedSocketException is thrown when attempting to perform an operation on a closed socket.
 */
public class ClosedSocketException extends Exception {
    private final networking.Client client;

    /**
     * Constructs a ClosedSocketException with a custom message and the associated client.
     *
     * @param client The client associated with the closed socket
     */
    public ClosedSocketException(networking.Client client) {
        super("Closed socket exception occurred for client: " + client);
        this.client = client;
    }

    /**
     * Gets the client associated with the closed socket.
     *
     * @return The client associated with the closed socket
     */
    @SuppressWarnings("unused")
    public networking.Client getClient() {
        return client;
    }
}