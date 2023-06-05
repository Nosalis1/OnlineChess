package socket;

import game.GameManager;
import socket.packages.Packet;
import socket.packages.PacketType;
import util.Console;

import java.net.Socket;

/**

 The LocalClient class extends the Client class and implements the Runnable interface.

 It represents a local client that connects to a server using a socket.
 */
public class LocalClient extends Client implements Runnable {
    public static final String IP_ADDER = "localhost";
    public static LocalClient instance;

    /**
     * Connects the local client to the server.
     * <p>
     * Displays messages and errors based on the connection status.
     */
    public static void connect() {
        util.Console.message("Trying to connect LocalClient", instance);
        if (instance != null) {
            util.Console.error("Failed to connect LocalClient. LocalClient is already connected!", instance);
            return;
        }

        try {
            instance = new LocalClient(new Socket(IP_ADDER, Server.PORT));
        } catch (Exception ex) {
            util.Console.error("Failed to connect LocalClient!", instance);
            ex.printStackTrace();
        }
        util.Console.message("LocalClient connected to PORT : " + Server.PORT + " with address : " + IP_ADDER, instance);
    }

    /**
     * Disconnects the local client from the server.
     * <p>
     * Sends a disconnect packet, closes the socket, and sets the instance to null.
     * <p>
     * Displays messages and warnings based on the disconnection status.
     */
    @SuppressWarnings("unused")
    public static void disconnect() {
        util.Console.message("Trying to disconnect LocalClient", instance);
        if (instance == null) {
            util.Console.warning("Failed to disconnect LocalClient. LocalClient is not connected!", null);
            return;
        }

        try {
            instance.send(new Packet(PacketType.DISCONNECT));
            instance.getSocket().close();
            instance = null;
        } catch (Exception ex) {
            util.Console.error("Failed to disconnect LocalClient!", instance);
            ex.printStackTrace();
        }
        util.Console.message("LocalClient disconnected", instance);
    }

    /**
     * Constructs a LocalClient object with the specified socket.
     * Starts a new thread for listening to incoming packets.
     *
     * @param socket the socket associated with the local client
     */
    public LocalClient(Socket socket) {
        super(socket);
        Thread listeningThread = new Thread(this);
        listeningThread.start();
    }

    private Packet packet = new Packet("");

    /**
     * Implementation of the run method from the Runnable interface.
     * <p>
     * Listens for incoming packets from the server and handles them.
     * <p>
     * Displays messages and errors based on the connection status and received packets.
     */
    @Override
    public void run() {
        if (getSocket().isClosed()) {
            util.Console.error("LocalClient Socket is closed!", this);
            return;
        }

        util.Console.message("LocalClient started listening", this);
        do {
            this.packet = receive();
        } while (handlePacket());
        util.Console.message("LocalClient stopped listening", this);
    }

    /**
     * Handles the received packet from the server.
     * Forwards the packet to the GameManager for further processing.
     *
     * @return true to continue listening for packets, false to stop listening
     */
    private boolean handlePacket() {
        Console.message("LocalClient received packet from Server", this);
        GameManager.handleNetworkPackage(packet);
        return true;
    }
}