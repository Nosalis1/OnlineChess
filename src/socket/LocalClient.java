package socket;

import game.GameManager;
import socket.packages.Packet;
import util.Console;

import java.net.Socket;

public class LocalClient extends Client implements Runnable {
    public static final String IP_ADDER = "localhost";
    public static LocalClient instance;

    public static void connect() {
        if (instance != null)
            return;

        try {
            instance = new LocalClient(new Socket(IP_ADDER, Server.PORT));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void disconnect() {
        if (instance == null)
            return;

        try {
            instance.send(Packet.DISCONNECTED);
            instance.getSocket().close();
            instance = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            Console.error("SOCKET IS CLOSED!", Console.PrintType.Socket);
            return;
        }

        Console.message("Client started listening", Console.PrintType.Socket);

        do {
            this.packet = receive(this.packet);
        } while (handlePacket());

        Console.message("Client stopped listening", Console.PrintType.Socket);
    }

    private boolean handlePacket() {
        Console.message("Client received packet from Server", Console.PrintType.Socket);
        GameManager.instance.handleNetworkPackage(packet);
        return true;
    }
}
