package socket.packages;

import util.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Stream {
    protected PrintWriter out;
    protected BufferedReader in;

    @SuppressWarnings("unused")
    public PrintWriter getOutStream() {
        return this.out;
    }

    @SuppressWarnings("unused")
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

    public void send(Packet packet) {
        out.println(packet.getPackedBuffer());
    }

    public Packet receive() {
        try {
            return new Packet(in.readLine());
        } catch (SocketException ex) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
