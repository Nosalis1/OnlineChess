package socket;

import game.GameManager;
import socket.packages.Packet;
import util.Console;

import java.net.Socket;

public class LocalClient extends Client implements Runnable {
    public static final String IP_ADDER = "localhost";
    public static LocalClient instance;

    public static void connect() {
        util.Console.message("Trying to connect LocalClient", instance);
        if (instance != null) {
            util.Console.error("Failed to connect LocalClient.LocalClient is already connected!", instance);
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

    public static void disconnect() {
        util.Console.message("Trying to disconnect LocalClient", instance);
        if (instance == null) {
            util.Console.warning("Failed to disconnect LocalClient.LocalClient is not connected!", null);
            return;
        }

        try {
            instance.send(new Packet("", Packet.Type.DISCONNECT));
            instance.getSocket().close();
            instance = null;
        } catch (Exception ex) {
            util.Console.error("Failed to disconnect LocalClient!", instance);
            ex.printStackTrace();
        }
        util.Console.message("LocalClient disconnected", instance);
    }

    public LocalClient(Socket socket) {
        super(socket);
        Thread listeningThread = new Thread(this);
        listeningThread.start();
    }

    private Packet packet = new Packet("");

    @Override
    public void run() {

        if (getSocket().isClosed()) {
            util.Console.error("LocalClient Socket is closed!", this);
            return;
        }

        util.Console.message("LocalClient started listening", this);
        do {
            this.packet = receive(this.packet);
        } while (handlePacket());
        util.Console.message("LocalClient stopped listening", this);
    }

    private boolean handlePacket() {
        Console.message("LocalClient received packet from Server", this);
        GameManager.instance.handleNetworkPackage(packet);
        return true;
    }
}
