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

        System.out.println("CLIENT START LISTENING");

        do {
            this.packet = receive(this.packet);
        } while (handlePacket());

        System.out.println("CLIENT STOP LISTENING");
    }

    private boolean handlePacket() {
        GameManager.instance.handleNetworkPackage(packet);
        return true;
    }
}
