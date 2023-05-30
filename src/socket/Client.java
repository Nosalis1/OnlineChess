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
        try {
            this.socket.setSoTimeout(0);
            super.initializeStreams(this.socket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Client(){}

    public Client(Socket socket) {
        setSocket(socket);
    }
}
