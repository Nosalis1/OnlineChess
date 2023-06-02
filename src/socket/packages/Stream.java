package socket.packages;

import util.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class Stream {
    protected PrintWriter out;
    protected BufferedReader in;

    public PrintWriter getOutStream() {
        return this.out;
    }

    public BufferedReader getInStream() {
        return this.in;
    }

    protected void initializeStreams(Socket socket) {
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            this.in = new BufferedReader(isr);
        } catch (Exception ex) {
            Console.error("Failed to initialize IN/OUT Streams!", this);
            ex.printStackTrace();
        }
    }

    //TODO: HANDLE SENDING/RECEIVING

    public void send(Packet packet) {
        out.println(packet.getPackedBuffer());
    }

    public Packet receive(Packet packet) {
        try {
            packet.setReceivedBuffer(in.readLine());
            return packet;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
