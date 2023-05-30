import socket.*;
import socket.events.ServerEventable;

import java.net.Socket;

public class NetworkManager implements ServerEventable {
    public static NetworkManager instance = null;

    public static boolean initialize() {
        if (instance != null)
            return false;

        instance = new NetworkManager();

        if (!Server.isMasterUp()) {
            Server.start();
            Server.startListening();
            return true;
        }
        return false;
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
