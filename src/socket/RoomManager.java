package socket;

import socket.packages.Packet;
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

        Packet packet = new Packet(Packet.Type.CHANGE_COLOR);
        clients.get(clients.size() - 1).send(packet);

        packet.setBuffer(Packet.Type.SEND_PLAYER);
        clients.get(0).send(packet);
        packet = clients.get(0).receive(packet);
        clients.get(1).send(packet);

        packet.setBuffer(Packet.Type.SEND_PLAYER);
        clients.get(1).send(packet);
        packet = clients.get(1).receive(packet);
        clients.get(0).send(packet);

        packet.setBuffer(Packet.Type.START_GAME);
        Packet finalPacket = packet;
        clients.foreach((Client client) -> client.send(finalPacket));

        Client whitePlayer = clients.get(0), blackPlayer = clients.get(1);

        if (whitePlayer.getSocket().isClosed() || blackPlayer.getSocket().isClosed()) {
            System.exit(-1);
        }

        boolean isWhiteTurn = true;

        while (true) {

            try {
                packet = isWhiteTurn ? whitePlayer.receive(packet) : blackPlayer.receive(packet);

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