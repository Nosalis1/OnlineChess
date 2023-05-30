import socket.Client;
import socket.ServerClient;
import socket.ServerRoom;
import socket.packages.Packet;
import util.Array;

import java.nio.channels.ClosedByInterruptException;

public class RoomManager {
    public static RoomManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new RoomManager();
    }

    public static final util.Array<ServerRoom> rooms = new Array<>();

    public static ServerRoom findOpenRoom() {
        for (int i = 0; i < rooms.size(); i++)
            if (rooms.get(i).isOpen())
                return rooms.get(i);
        return null;
    }

    public static ServerRoom createNewRoom() {
        ServerRoom newRoom = new ServerRoom();
        rooms.add(newRoom);
        return newRoom;
    }

    public RoomManager() {
        ServerRoom.onRoomStarted.add(this::onRoomStarted);
    }

    public void onRoomStarted(ServerRoom room) {
        handleRoomProcess(room);
    }

    private void handleRoomProcess(ServerRoom room) {

        //TODO : DO GAME LOGIC
        util.Array<Client> clients = room.getClients();

        clients.foreach((Client client)->{
            if(client.getSocket().isClosed()){
                System.out.println("CLOSED SERVER CLIENT");
            }
        });

        clients.get(clients.size() - 1).send(Packet.CHANGE_COLOR);
        clients.foreach((Client client) -> {
            client.send(Packet.START_GAME);
        });

        Client whitePlayer = clients.get(0), blackPlayer = clients.get(1);

        if(whitePlayer.getSocket().isClosed() || blackPlayer.getSocket().isClosed()) {
            System.exit(-1);
        }


        Packet packet = new Packet("");
        boolean isWhiteTurn = true;

        while (true) {

            try {
                packet = isWhiteTurn ? whitePlayer.receive(packet) : blackPlayer.receive(packet);

                if (isWhiteTurn)
                    blackPlayer.send(packet);
                else
                    whitePlayer.send(packet);

            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            isWhiteTurn = !isWhiteTurn;
        }
    }
}