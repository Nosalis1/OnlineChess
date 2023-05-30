package socket;

import socket.packages.Packet;

import java.net.Socket;

public class ServerClient extends Client {
    public ServerClient(Socket socket) {
        super(socket);
    }

    @Override
    public void send(Packet packet){
        super.send(packet);
        System.out.println("SERVER SENT PACKET");
    }
}
