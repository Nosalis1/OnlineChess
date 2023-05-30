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
        util.Console.message("Server received packet from client.", Console.PrintType.Socket);
        return super.receive(packet);
    }

    @Override
    public void send(Packet packet) {
        util.Console.message("Server sent packet to client.", Console.PrintType.Socket);
        super.send(packet);
    }
}
