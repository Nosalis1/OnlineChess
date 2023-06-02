package socket;

import socket.packages.Packet;
import util.Console;

import java.net.Socket;

public class ServerClient extends Client {
    public ServerClient(Socket socket) {
        super(socket);
    }

    @Override
    public Packet receive(Packet packet){
        util.Console.message("Server received packet from client.", this);
        return super.receive(packet);
    }

    @Override
    public void send(Packet packet) {
        util.Console.message("Server sent packet to client.", this);
        super.send(packet);
    }
}
