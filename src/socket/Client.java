package socket;

import socket.packages.Stream;

import java.net.Socket;

public class Client extends Stream {
    private Socket socket;

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        super.initializeStreams(this.socket);
    }

    public Client(){}

    public Client(Socket socket) {
        setSocket(socket);
    }
}
