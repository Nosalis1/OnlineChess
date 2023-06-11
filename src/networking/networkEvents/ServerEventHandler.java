package networking.networkEvents;

import utility.math.Array;

/**
 * The ServerEventHandler class manages server event actions and provides functionality to handle server events.
 */
public class ServerEventHandler extends Array<IServerEvents> implements IServerEvents {

    /**
     * Adds a server event action to the list.
     * Displays a warning message if the action already exists in the list.
     *
     * @param eventActions The server event action to be added
     */
    @Override
    public void add(IServerEvents eventActions) {
        if (contains(eventActions)) {
            utility.Console.warning("The action you are trying to add already exists in the list!", this);
            return;
        }

        super.add(eventActions);
    }

    /**
     * Notifies all registered server event actions that the server has started.
     */
    @Override
    public void onServerStarted() {
        super.forEach((IServerEvents eventActions) -> {
            try {
                eventActions.onServerStarted();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Notifies all registered server event actions that the server has stopped.
     */
    @Override
    public void onServerStopped() {
        super.forEach((IServerEvents eventActions) -> {
            try {
                eventActions.onServerStopped();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Notifies all registered server event actions that the server has started listening for incoming connections.
     */
    @Override
    public void onServerStartListening() {
        super.forEach((IServerEvents eventActions) -> {
            try {
                eventActions.onServerStartListening();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Notifies all registered server event actions that the server has stopped listening for incoming connections.
     */
    @Override
    public void onServerStopListening() {
        super.forEach((IServerEvents eventActions) -> {
            try {
                eventActions.onServerStopListening();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Notifies all registered server event actions that a socket connection has been established with a client.
     *
     * @param clientSocket The socket representing the client connection
     */
    @Override
    public void onSocketConnected(java.net.Socket clientSocket) {
        super.forEach((IServerEvents eventActions) -> {
            try {
                eventActions.onSocketConnected(clientSocket);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}