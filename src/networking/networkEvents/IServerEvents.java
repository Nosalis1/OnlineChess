package networking.networkEvents;

/**
 * Interface defining server events.
 */
public interface IServerEvents {
    /**
     * Called when the server has started.
     */
    void onServerStarted();

    /**
     * Called when the server has stopped.
     */
    void onServerStopped();

    /**
     * Called when the server starts listening for incoming connections.
     */
    void onServerStartListening();

    /**
     * Called when the server stops listening for incoming connections.
     */
    void onServerStopListening();

    /**
     * Called when a socket is connected to the server.
     *
     * @param clientSocket the connected client socket
     */
    void onSocketConnected(java.net.Socket clientSocket);
}