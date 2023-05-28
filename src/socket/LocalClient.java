package socket;

import socket.packages.Packet;

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
            //TODO: SEND SERVER MSG TO DISCONNECT
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

        do {
            this.packet = receive(this.packet);
        } while (handlePacket());

    }

    private boolean handlePacket() {

        //TODO: HANDLE PACKET

        return true;
    }
}
