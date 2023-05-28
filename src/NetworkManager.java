import socket.LocalClient;
import socket.Server;
import socket.ServerClient;
import socket.ServerRoom;
import socket.events.ServerEventable;

import java.net.Socket;

public class NetworkManager implements ServerEventable {
    public static NetworkManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new NetworkManager();

        if (Server.isMasterUp()) {
            LocalClient.connect();
            LocalClient.disconnect();
        } else {
            Server.start();
            Server.startListening();
            //Server.stop();
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

        ServerClient serverClient = new ServerClient(clientSocket);
        room.clientTryJoin(serverClient);
    }
}
