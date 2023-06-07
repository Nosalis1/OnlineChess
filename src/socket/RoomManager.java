package socket;

import socket.exceptions.ClosedSocketException;
import socket.exceptions.NullPacketException;
import socket.packages.Packet;
import util.Array;
import util.Console;

/**
 * The RoomManager class manages the creation and handling of server rooms.
 */
public class RoomManager {

    /**
     * Initializes the RoomManager.
     */
    public static void initialize() {
        Console.message("Initializing RoomManager.");
        ServerRoom.onRoomStarted.add(RoomManager::onRoomStarted);
    }

    /**
     * An array of server rooms.
     */
    public static final Array<ServerRoom> rooms = new Array<>();

    /**
     * Finds an open server room.
     *
     * @return The first open server room found, or null if no open room is available
     */
    public static ServerRoom findOpenRoom() {
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).isOpen()) {
                return rooms.get(i);
            }
        }
        return null;
    }

    /**
     * Creates a new server room.
     *
     * @return The newly created server room
     */
    public static ServerRoom createNewRoom() {
        ServerRoom newRoom = new ServerRoom();
        rooms.add(newRoom);
        return newRoom;
    }

    /**
     * Handles the room started event.
     *
     * @param room The server room that started
     */
    public static void onRoomStarted(ServerRoom room) {
        try {
            handleRoomProcess(room);
        } catch (ClosedSocketException ex) {
            Console.error("Client Socket is closed!");
        }
    }

    /**
     * Exchanges player names between two clients.
     *
     * @param first  The first client
     * @param second The second client
     */
    private static void exchangeNames(Client first, Client second) {
        first.send(Packet.SEND_PLAYER);
        Packet packet = first.receive();
        second.send(packet);

        second.send(Packet.SEND_PLAYER);
        packet = second.receive();
        first.send(packet);
    }

    /**
     * Handles the process of a server room.
     *
     * @param room The server room to handle
     * @throws ClosedSocketException If a client's socket is closed
     */
    private static void handleRoomProcess(ServerRoom room) throws ClosedSocketException {
        Array<Client> clients = room.getClients();

        Client whitePlayer = clients.get(0), blackPlayer = clients.getLast();

        if (whitePlayer.getSocket().isClosed())
            throw new ClosedSocketException(whitePlayer);
        if (blackPlayer.getSocket().isClosed())
            throw new ClosedSocketException(blackPlayer);

        blackPlayer.send(Packet.CHANGE_COLOR);

        exchangeNames(whitePlayer, blackPlayer);

        clients.forEach(client -> client.send(Packet.START_GAME));

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
                Console.error("Client Socket is closed!");
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
                break;
            }
            sender = !sender;
        }
    }
}