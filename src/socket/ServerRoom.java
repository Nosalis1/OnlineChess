package socket;

import util.Array;
import util.events.ArgEvent;

public class ServerRoom implements Runnable {
    public static final int MAX_CLIENTS = 2;

    private final util.Array<Client> clients = new Array<>();

    public util.Array<Client> getClients() {
        return this.clients;
    }

    private boolean open = true;

    public final boolean isOpen() {
        return this.open;
    }

    private boolean inProgress = false;

    @SuppressWarnings("unused")
    public final boolean isInProgress() {
        return this.inProgress;
    }

    public void clientTryJoin(Client client) {
        util.Console.message("Client is trying to join a room", null);
        if (!isOpen()) {
            util.Console.warning("Client failed to join a room.Room is closed", null);
            return;
        }

        if (clients.contains(client)) {
            util.Console.warning("Client failed to join a room.Client is already in this room", null);
            return;
        }

        clients.add(client);
        util.Console.message("Client joined the room", null);

        if (clients.size() >= MAX_CLIENTS) {
            util.Console.warning("ROOM MAX_CLIENT_SIZE exceeded!", null);
            util.Console.message("Closing the room", null);
            open = false;
            util.Console.message("Room closed", null);
            util.Console.message("Creating room thread", null);
            Thread roomThread = new Thread(this);
            util.Console.message("Starting room thread", null);
            roomThread.start();
        }
    }

    public ServerRoom() {
        util.Console.message("New Server Room created", null);
    }

    public static util.events.ArgEvent<ServerRoom> onRoomStarted = new ArgEvent<>();

    @Override
    public void run() {
        inProgress = true;

        util.Console.message("Room process started", null);

        onRoomStarted.run(this);

        util.Console.message("Room process finished", null);
        inProgress = false;
    }
}