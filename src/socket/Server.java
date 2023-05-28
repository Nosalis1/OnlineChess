package socket;

import socket.events.ServerEventHandler;
import util.Array;
import util.Console;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 51130;
    public static final int MAX_SOCKET_CONNECTIONS = 2;

    private static ServerSocket serverSocket = null;

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static ServerEventHandler eventHandler = new ServerEventHandler();

    // TODO : MAIN
    public static void start() {
        Console.message("Trying to start the Server on port " + PORT, Console.PrintType.Socket);
        if (isMasterUp() || isMasterActive()) {
            Console.error("Failed to start the Server.Server is already UP!", Console.PrintType.Socket);
            return;
        }

        Console.message("Trying to initialize ServerSocket", Console.PrintType.Socket);
        try {
            serverSocket = new ServerSocket(PORT);

            Console.message("ServerSocket initialized.Server started", Console.PrintType.Socket);
            eventHandler.onServerStarted();
        } catch (Exception ex) {
            Console.error("Failed to initialize ServerSocket!", Console.PrintType.Socket);
            ex.printStackTrace();
        }
    }

    public static void stop() {
        if (isListening())
            stopListening();

        Console.message("Trying to stop the Server on port " + PORT, Console.PrintType.Socket);
        if (isClosed()) {
            Console.error("Failed to stop the Server.Server is already CLOSED!", Console.PrintType.Socket);
            return;
        }

        Console.message("Trying to stop ServerSocket", Console.PrintType.Socket);
        try {
            serverSocket.close();

            Console.message("ServerSocket stopped.Server stopped", Console.PrintType.Socket);
            eventHandler.onServerStopped();
        } catch (Exception ex) {
            Console.error("Failed to stop ServerSocket!", Console.PrintType.Socket);
            ex.printStackTrace();
        }
    }

    // TODO : VALIDATIONS
    public static boolean isNull() {
        return getServerSocket() == null;
    }

    public static boolean isClosed() {
        return isNull() || getServerSocket().isClosed();
    }

    public static boolean isMasterActive() {
        return !isClosed();
    }

    public static boolean isMasterUp() {
        try (ServerSocket newSocket = new ServerSocket(PORT)) {
            newSocket.close();
        } catch (Exception ex) {
            return true;
        }
        return false;
    }

    // TODO : CONNECTIONS

    private static final util.Array<Socket> connectedSockets = new Array<>();

    public static util.Array<Socket> getConnectedSockets() {
        return connectedSockets;
    }

    private static boolean listening = false;

    public static boolean isListening() {
        return listening;
    }

    public static void startListening() {
        Console.message("Trying to start the Server Listening process", Console.PrintType.Socket);
        if (isListening()) {
            Console.error("Failed to start the Server Listening process.Server is already Listening!", Console.PrintType.Socket);
            return;
        }

        listening = true;

        Console.message("Server started Listening", Console.PrintType.Socket);
        eventHandler.onServerStartListening();

        while (isListening()) {

            Console.message("Waiting for new connection...", Console.PrintType.Socket);
            try (Socket clientSocket = serverSocket.accept()) {

                handleNewConnection(clientSocket);

                if (connectedSockets.size() >= MAX_SOCKET_CONNECTIONS)
                    stopListening();

            } catch (Exception ex) {
                Console.error("Failed to accept connection from ClientSocket!", Console.PrintType.Socket);
                ex.printStackTrace();
                break;
            }

        }

        Console.message("Server stopped Listening", Console.PrintType.Socket);
        eventHandler.onServerStopListening();
    }

    public static void stopListening() {
        if (!isListening()) {
            Console.error("Failed to stop Listening process.Server is already not Listening!", Console.PrintType.Socket);
            return;
        }
        Console.message("Trying to stop Listening process", Console.PrintType.Socket);
        listening = false;
    }

    private static void handleNewConnection(Socket clientSocket) {
        Console.message("New connection received IP_ADDER " + clientSocket.getInetAddress(), Console.PrintType.Socket);
        if (connectedSockets.contains(clientSocket)) {
            Console.error("Failed to register new Socket.Socket is already registered!", Console.PrintType.Socket);
            return;
        }

        connectedSockets.add(clientSocket);

        Console.message("New connection Socket successfully registered", Console.PrintType.Socket);
        eventHandler.onSocketConnected(clientSocket);
    }

    @Deprecated
    public static int determineFreePort() {
        int freePort = 51130;
        boolean isOccupied = true;

        while (isOccupied) {
            try (ServerSocket newSocket = new ServerSocket(freePort)) {
                isOccupied = false;
            } catch (Exception ex) {
                ++freePort;
            }
        }

        return freePort;
    }
}
