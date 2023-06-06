package socket;

import socket.exceptions.ClosedSocketException;
import socket.exceptions.NullPacketException;
import socket.packages.Packet;
import util.Array;
import util.Console;

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
        try {
            handleRoomProcess(room);
        } catch (ClosedSocketException ex) {
            Console.error("Client Socket is closed!", this);
        }
    }

    private void exchangeNames(final Client first, final Client second) {
        first.send(Packet.SEND_PLAYER);
        Packet packet = first.receive();
        second.send(packet);

        second.send(Packet.SEND_PLAYER);
        packet = second.receive();
        first.send(packet);
    }

    private void handleRoomProcess(ServerRoom room) throws ClosedSocketException {
        util.Array<Client> clients = room.getClients();

        Client whitePlayer = clients.get(0), blackPlayer = clients.getLast();

        if (whitePlayer.getSocket().isClosed())
            throw new ClosedSocketException(whitePlayer);
        if (blackPlayer.getSocket().isClosed())
            throw new ClosedSocketException(blackPlayer);

        blackPlayer.send(Packet.CHANGE_COLOR);

        exchangeNames(whitePlayer, blackPlayer);

        clients.forEach((Client client) -> client.send(Packet.START_GAME));

        boolean sender = true;
        Packet packet;

        while (true) {

            try {
                packet = sender ? whitePlayer.receive() : blackPlayer.receive();

                if (packet == null)
                    throw new NullPacketException();

                if (sender)
                    blackPlayer.send(packet);
                else
                    whitePlayer.send(packet);

            } catch (NullPacketException ex) {
                Console.error("Client Socket is closed!", this);
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            sender = !sender;
        }
    }
}