import socket.LocalClient;
import socket.Server;
import socket.ServerClient;
import socket.ServerRoom;
import socket.events.ServerEventable;

import java.net.Socket;

public class NetworkManager implements ServerEventable {
    public static NetworkManager instance;

    public static boolean initialize() {
        if (Server.isMasterUp())
            return false;

        if (instance == null)
            instance = new NetworkManager();

        Server.start();
        Server.startListening();
        //Server.stop();
        return true;
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

        ServerClient serverClient = new ServerClient(clientSocket);
        room.clientTryJoin(serverClient);
    }
}
