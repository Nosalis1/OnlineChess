package networking;

import networking.packageSystem.Stream;

import java.net.Socket;

/**
 * The Client class extends the Stream class and represents a client connection.
 * <p>
 * It encapsulates a socket object for communication with the server.
 */
public class Client extends Stream {
    private Socket socket;

    /**
     * Retrieves the socket associated with the client.
     *
     * @return The client's socket
     */
    public Socket getSocket() {
        return this.socket;
    }

    /**
     * Sets the socket for the client and initializes the streams.
     *
     * @param socket The socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.socket.setSoTimeout(0);
            super.initializeStreams(this.socket);
        } catch (Exception ex) {
            utility.Console.error("Failed to set Client socket!", this);
            ex.printStackTrace();
        }
    }

    /**
     * Constructs a Client object with the specified socket.
     *
     * @param socket The socket associated with the client
     */
    public Client(Socket socket) {
        setSocket(socket);
    }
}