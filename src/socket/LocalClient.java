package socket;

import game.GameManager;
import socket.packages.Packet;
import util.Console;

import java.net.Socket;

public class LocalClient extends Client implements Runnable {
    public static final String IP_ADDER = "localhost";
    public static LocalClient instance;

    public static void connect() {
        util.Console.message("Trying to connect LocalClient", Console.PrintType.Socket);
        if (instance != null) {
            util.Console.error("Failed to connect LocalClient.LocalClient is already connected!", Console.PrintType.Socket);
            return;
        }

        try {
            instance = new LocalClient(new Socket(IP_ADDER, Server.PORT));
        } catch (Exception ex) {
            util.Console.error("Failed to connect LocalClient!", Console.PrintType.Socket);
            ex.printStackTrace();
        }
        util.Console.message("LocalClient connected to PORT : " + Server.PORT + " with address : " + IP_ADDER, Console.PrintType.Socket);
    }

    public static void disconnect() {
        util.Console.message("Trying to disconnect LocalClient", Console.PrintType.Socket);
        if (instance == null) {
            util.Console.warning("Failed to disconnect LocalClient.LocalClient is not connected!", Console.PrintType.Socket);
            return;
        }

        try {
            instance.send(new Packet("",Packet.Type.DISCONNECT));
            instance.getSocket().close();
            instance = null;
        } catch (Exception ex) {
            util.Console.error("Failed to disconnect LocalClient!", Console.PrintType.Socket);
            ex.printStackTrace();
        }
        util.Console.message("LocalClient disconnected", Console.PrintType.Socket);
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
            util.Console.error("LocalClient Socket is closed!", Console.PrintType.Socket);
            return;
        }

        util.Console.message("LocalClient started listening", Console.PrintType.Socket);
        do {
            this.packet = receive(this.packet);
        } while (handlePacket());
        util.Console.message("LocalClient stopped listening", Console.PrintType.Socket);
    }

    private boolean handlePacket() {
        Console.message("LocalClient received packet from Server", Console.PrintType.Socket);
        GameManager.instance.handleNetworkPackage(packet);
        return true;
    }
}
