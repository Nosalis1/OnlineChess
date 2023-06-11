package networking;

import networking.networkEvents.IServerEvents;

import java.net.Socket;

public class NetworkManager implements IServerEvents {
    public static NetworkManager instance = null;

    /**
     * Check if the current server is the master server
     */
    public static boolean isMaster() {
        return !Server.isMasterUp();
    }

    /**
     * Connect a client to the network
     */
    public static void connectClient() {
        LocalClient.connect();
    }

    /**
     * Disconnect a client from the network
     */
    @SuppressWarnings("unused")
    public static void disconnectClient() {
        LocalClient.disconnect();
    }

    /**
     * Initialize the NetworkManager
     */
    public static void initialize() {
        utility.Console.message("Initializing NetworkManager.", instance);

        if (instance != null)
            return;

        instance = new NetworkManager();

        // If the master server is not up, start the server and listen for connections
        if (!Server.isMasterUp()) {
            Server.start();
            Server.startListening();
        }
    }

    /**
     * Constructor for NetworkManager
     */
    public NetworkManager() {
        Server.eventHandler.add(this);
    }

    /**
     * Called when the server is started
     */
    @Override
    public void onServerStarted() {
        System.out.println();
    }

    /**
     * Called when the server is stopped
     */
    @Override
    public void onServerStopped() {
        System.out.println();
    }

    /**
     * Called when the server starts listening for connections
     */
    @Override
    public void onServerStartListening() {
        System.out.println();
    }

    /**
     * Called when the server stops listening for connections
     */
    @Override
    public void onServerStopListening() {
        System.out.println();
    }

    /**
     * Called when a socket is connected to the server
     *
     * @param clientSocket The connected client socket
     */
    @Override
    public void onSocketConnected(Socket clientSocket) {
        ServerRoom room = RoomManager.findOpenRoom();
        room = room == null ? RoomManager.createNewRoom() : room;

        room.clientTryJoin(new Client(clientSocket));
    }
}