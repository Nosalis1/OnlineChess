package socket.packages;

import util.Console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * The Stream class provides functionality for managing input and output streams
 * used for sending and receiving data over a socket connection.
 */
public abstract class Stream {
    protected PrintWriter out;
    protected BufferedReader in;

    /**
     * Get the output stream used for sending data.
     *
     * @return The PrintWriter output stream
     */
    @SuppressWarnings("unused")
    public PrintWriter getOutStream() {
        return this.out;
    }

    /**
     * Get the input stream used for receiving data.
     *
     * @return The BufferedReader input stream
     */
    @SuppressWarnings("unused")
    public BufferedReader getInStream() {
        return this.in;
    }

    /**
     * Initialize the input and output streams using the provided socket.
     *
     * @param socket The socket used for communication
     */
    protected void initializeStreams(Socket socket) {
        Console.message("Initializing IN/OUT Streams.", this);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            this.in = new BufferedReader(isr);
        } catch (Exception ex) {
            Console.error("Failed to initialize IN/OUT Streams!", this);
            ex.printStackTrace();
        }
        Console.message("IN/OUT Streams initialized", this);
    }

    /**
     * Send a packet over the output stream.
     *
     * @param packet The packet to send
     */
    public void send(Packet packet) {
        out.println(packet.getPackedBuffer());
    }

    /**
     * Receive a packet from the input stream.
     *
     * @return The received packet
     */
    public Packet receive() {
        try {
            return new Packet(in.readLine());
        } catch (SocketException ex) {
            Console.error("SocketException thrown while receiving the package!", this);
            ex.printStackTrace();
        } catch (Exception ex) {
            Console.error("Failed to read the package!", this);
            ex.printStackTrace();
        }
        return null;
    }
}