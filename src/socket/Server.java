package socket;

import socket.events.ServerEventHandler;
import util.Array;
import util.Console;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server class represents a server application.
 * <p>
 * It provides methods for starting and stopping the server, managing connections,
 * <p>
 * and validating the server status.
 */
public class Server {
    public static final int PORT = 52130;
    public static final int MAX_SOCKET_CONNECTIONS = 4;

    private static ServerSocket serverSocket = null;

    /**
     * Retrieves the server socket.
     *
     * @return the server socket
     */
    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * The event handler for server events.
     */
    public static ServerEventHandler eventHandler = new ServerEventHandler();

    /**
     * Starts the server.
     * <p>
     * Displays messages and errors based on the server status.
     */
    public static void start() {
        Console.message("Trying to start the Server on port " + PORT, null);
        if (isMasterUp() || isMasterActive()) {
            Console.error("Failed to start the Server. Server is already UP!", null);
            return;
        }

        Console.message("Trying to initialize ServerSocket", null);
        try {
            serverSocket = new ServerSocket(PORT);

            Console.message("ServerSocket initialized.Server started", null);
            eventHandler.onServerStarted();
        } catch (Exception ex) {
            Console.error("Failed to initialize ServerSocket!", null);
            ex.printStackTrace();
        }
    }

    /**
     * Stops the server.
     * <p>
     * Stops listening, closes the server socket, and triggers server stop events.
     * <p>
     * Displays messages and errors based on the server status.
     */
    @SuppressWarnings("unused")
    public static void stop() {
        if (isListening()) stopListening();

        Console.message("Trying to stop the Server on port " + PORT, null);
        if (isClosed()) {
            Console.error("Failed to stop the Server. Server is already CLOSED!", null);
            return;
        }

        Console.message("Trying to stop ServerSocket", null);
        try {
            serverSocket.close();

            Console.message("ServerSocket stopped.Server stopped", null);
            eventHandler.onServerStopped();
        } catch (Exception ex) {
            Console.error("Failed to stop ServerSocket!", null);
            ex.printStackTrace();
        }
    }

    /**
     * Checks if the server socket is null.
     *
     * @return true if the server socket is null, false otherwise
     */
    public static boolean isNull() {
        return getServerSocket() == null;
    }

    /**
     * Checks if the server socket is closed.
     *
     * @return true if the server socket is closed, false otherwise
     */
    public static boolean isClosed() {
        return isNull() || getServerSocket().isClosed();
    }

    /**
     * Checks if the server is active.
     *
     * @return true if the server is active, false otherwise
     */
    public static boolean isMasterActive() {
        return !isClosed();
    }

    /**
     * Checks if the server is up by attempting to create a new socket.
     *
     * @return true if the server is up, false otherwise
     */
    public static boolean isMasterUp() {
        try (ServerSocket ignored = new ServerSocket(PORT)) {
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    private static final util.Array<Socket> connectedSockets = new Array<>();

    /**
     * Retrieves the array of connected sockets.
     *
     * @return the array of connected sockets
     */
    @SuppressWarnings("unused")
    public static util.Array<Socket> getConnectedSockets() {
        return connectedSockets;
    }

    private static boolean listening = false;

    /**
     * Checks if the server is currently listening for new connections.
     *
     * @return true if the server is listening, false otherwise
     */
    public static boolean isListening() {
        return listening;
    }

    /**
     * Starts the server listening process.
     * <p>
     * Displays messages and errors based on the server status.
     */
    public static void startListening() {
        Console.message("Trying to start the Server Listening process", null);
        if (isListening()) {
            Console.error("Failed to start the Server Listening process.Server is already Listening!", null);
            return;
        }

        listening = true;

        Console.message("Server started Listening", null);
        eventHandler.onServerStartListening();

        while (isListening()) {

            Console.message("Waiting for new connection...", null);
            try {
                Socket clientSocket = serverSocket.accept();
                handleNewConnection(clientSocket);

                if (connectedSockets.size() >= MAX_SOCKET_CONNECTIONS) stopListening();

            } catch (Exception ex) {
                Console.error("Failed to accept connection from ClientSocket!", null);
                ex.printStackTrace();
                break;
            }

        }

        Console.message("Server stopped Listening", null);
        eventHandler.onServerStopListening();
    }

    /**
     * Stops the server listening process.
     * Displays messages and errors based on the server status.
     */
    public static void stopListening() {
        if (!isListening()) {
            Console.error("Failed to stop Listening process.Server is already not Listening!", null);
            return;
        }
        Console.message("Trying to stop Listening process", null);
        listening = false;
    }

    /**
     * Handles a new connection from a client socket.
     * <p>
     * Registers the socket and triggers socket connected events.
     *
     * @param clientSocket the client socket to handle
     */
    private static void handleNewConnection(Socket clientSocket) {
        Console.message("New connection received IP_ADDER " + clientSocket.getInetAddress(), null);
        if (connectedSockets.contains(clientSocket)) {
            Console.error("Failed to register new Socket.Socket is already registered!", null);
            return;
        }

        connectedSockets.add(clientSocket);

        Console.message("New connection Socket successfully registered", null);
        eventHandler.onSocketConnected(clientSocket);
    }

    /**
     * [DEPRECATED] Determines a free port by checking for availability.
     * <p>
     * This method is deprecated and should not be used.
     *
     * @return the determined free port number
     */
    @Deprecated
    public static int determineFreePort() {
        int freePort = 51130;
        boolean isOccupied = true;

        while (isOccupied) {
            try (ServerSocket ignored = new ServerSocket(freePort)) {
                isOccupied = false;
            } catch (Exception ex) {
                ++freePort;
            }
        }

        return freePort;
    }
}