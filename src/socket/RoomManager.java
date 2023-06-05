package socket;

import socket.packages.Packet;
import socket.packages.PacketType;
import util.Array;

public class RoomManager {
    public static RoomManager instance;

    public static void initialize() {
        util.Console.message("Initializing RoomManager.", instance);

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
        util.Array<Client> clients = room.getClients();

        clients.foreach((Client client) -> {
            if (client.getSocket().isClosed()) {
                System.out.println("CLOSED SERVER CLIENT");
            }
        });

        clients.get(clients.size() - 1).send(new Packet(PacketType.CHANGE_COLOR));

        clients.get(0).send(new Packet(PacketType.SEND_PLAYER));
        Packet packet = clients.get(0).receive();
        clients.get(1).send(packet);

        clients.get(1).send(new Packet(PacketType.SEND_PLAYER));
        packet = clients.get(1).receive();
        clients.get(0).send(packet);

        clients.foreach((Client client) -> client.send(new Packet(PacketType.START_GAME)));

        Client whitePlayer = clients.get(0), blackPlayer = clients.get(1);

        if (whitePlayer.getSocket().isClosed() || blackPlayer.getSocket().isClosed()) {
            System.exit(-1);
        }

        boolean isWhiteTurn = true;

        while (true) {

            try {
                packet = isWhiteTurn ? whitePlayer.receive() : blackPlayer.receive();

                if (packet == null)
                    break;

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