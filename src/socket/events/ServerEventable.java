package socket.events;

import java.net.Socket;

public interface ServerEventable {
    /**
     * Called when the server has started.
     */
    public void onServerStarted();

    /**
     * Called when the server has stopped.
     */
    public void onServerStopped();

    /**
     * Called when the server starts listening for incoming connections.
     */
    public void onServerStartListening();

    /**
     * Called when the server stops listening for incoming connections.
     */
    public void onServerStopListening();

    /**
     * Called when a socket is connected to the server.
     *
     * @param clientSocket the connected client socket
     */
    public void onSocketConnected(Socket clientSocket);

}
