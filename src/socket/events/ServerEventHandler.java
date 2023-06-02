package socket.events;

import java.net.Socket;

/**
 * The ServerEventHandler class extends the util.Array class and implements the ServerEventable interface.
 * It provides functionality to handle server events and manage a list of server event actions.
 */
public class ServerEventHandler extends util.Array<ServerEventable> implements ServerEventable {

    /**
     * Adds a server event action to the list.
     * Displays a warning message if the action already exists in the list.
     *
     * @param actions The server event action to be added
     */
    @Override
    public void add(ServerEventable actions) {
        if (contains(actions)) {
            util.Console.warning("Action you are trying to add already exists in the list!", this);
            return;
        }

        super.add(actions);
    }

    /**
     * Notifies all registered server event actions that the server has started.
     */
    @Override
    public void onServerStarted() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStarted();
    }

    /**
     * Notifies all registered server event actions that the server has stopped.
     */
    @Override
    public void onServerStopped() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStopped();
    }

    /**
     * Notifies all registered server event actions that the server has started listening for incoming connections.
     */
    @Override
    public void onServerStartListening() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStartListening();
    }

    /**
     * Notifies all registered server event actions that the server has stopped listening for incoming connections.
     */
    @Override
    public void onServerStopListening() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStopListening();
    }

    /**
     * Notifies all registered server event actions that a socket connection has been established with a client.
     *
     * @param clientSocket The socket representing the client connection
     */
    @Override
    public void onSocketConnected(Socket clientSocket) {
        for (int i = 0; i < size(); i++)
            get(i).onSocketConnected(clientSocket);
    }
}