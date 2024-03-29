package networking;

import utility.math.Array;
import utility.eventSystem.ArgumentEvent;

/**

 The ServerRoom class represents a server room where clients can join and participate in a game.

 It manages the clients in the room, controls the room's state, and handles the room process.
 */
public class ServerRoom implements Runnable {
    public static final int MAX_CLIENTS = 2;

    private final Array<Client> clients = new Array<>();

    /**
     * Retrieves the array of clients in the room.
     *
     * @return the array of clients
     */
    public Array<Client> getClients() {
        return this.clients;
    }

    private boolean open = true;

    /**
     * Checks if the room is open for new clients to join.
     *
     * @return true if the room is open, false otherwise
     */
    public final boolean isOpen() {
        return this.open;
    }

    private boolean inProgress = false;

    /**
     * Checks if the room process is in progress.
     *
     * @return true if the room process is in progress, false otherwise
     */
    @SuppressWarnings("unused")
    public final boolean isInProgress() {
        return this.inProgress;
    }

    /**
     * Attempts to let a client join the room.
     * <p>
     * Displays messages and warnings based on the joining process and room status.
     *
     * @param client the client trying to join the room
     */
    public void clientTryJoin(Client client) {
        utility.Console.message("Client is trying to join a room", null);
        if (!isOpen()) {
            utility.Console.warning("Client failed to join a room. Room is closed", null);
            return;
        }

        if (clients.contains(client)) {
            utility.Console.warning("Client failed to join a room. Client is already in this room", null);
            return;
        }

        clients.add(client);
        utility.Console.message("Client joined the room", null);

        if (clients.size() >= MAX_CLIENTS) {
            utility.Console.warning("ROOM MAX_CLIENT_SIZE exceeded!", null);
            utility.Console.message("Closing the room", null);
            open = false;
            utility.Console.message("Room closed", null);
            utility.Console.message("Creating room thread", null);
            Thread roomThread = new Thread(this);
            utility.Console.message("Starting room thread", null);
            roomThread.start();
        }
    }

    /**
     * Constructs a new ServerRoom object.
     * Displays a message indicating the creation of the room.
     */
    public ServerRoom() {
        utility.Console.message("New Server Room created", null);
    }

    /**
     * Event handler for when the room is started.
     * It provides an argument event with the server room as the argument.
     */
    public static ArgumentEvent<ServerRoom> onRoomStarted = new ArgumentEvent<>();

    @Override
    public void run() {
        inProgress = true;

        utility.Console.message("Room process started", null);

        onRoomStarted.run(this);

        utility.Console.message("Room process finished", null);
        inProgress = false;
    }
}