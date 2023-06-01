package socket;

import socket.events.ServerEventable;
import util.Console;

import java.net.Socket;

public class NetworkManager implements ServerEventable {
    public static NetworkManager instance = null;

    public static boolean isMaster() {
        return !Server.isMasterUp();
    }

    public static void connectClient() {
        LocalClient.connect();
    }

    public static void initialize() {
        util.Console.message("Initializing NetworkManager.", Console.PrintType.Main);

        if (instance != null)
            return;

        instance = new NetworkManager();

        if (!Server.isMasterUp()) {
            Server.start();
            Server.startListening();
        }
    }

    public NetworkManager() {
        Server.eventHandler.add(this);
    }

    @Override
    public void onServerStarted() {
        System.out.println("Server start TEST");
    }

    @Override
    public void onServerStopped() {
        System.out.println("Server stop TEST");
    }

    @Override
    public void onServerStartListening() {
        System.out.println("Listening start TEST");
    }

    @Override
    public void onServerStopListening() {
        System.out.println("Listening stop TEST");
    }

    @Override
    public void onSocketConnected(Socket clientSocket) {
        ServerRoom room = RoomManager.findOpenRoom();
        room = room == null ? RoomManager.createNewRoom() : room;

        room.clientTryJoin(new Client(clientSocket));
    }
}