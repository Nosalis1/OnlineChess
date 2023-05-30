package socket;

import util.Array;
import util.Console;
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

    public final boolean isInProgress() {
        return this.inProgress;
    }

    public void clientTryJoin(Client client) {
        util.Console.message("Client is trying to join a room", Console.PrintType.Socket);
        if (!isOpen()) {
            util.Console.warning("Client failed to join a room.Room is closed", Console.PrintType.Socket);
            return;
        }

        if (clients.contains(client)) {
            util.Console.warning("Client failed to join a room.Client is already in this room", Console.PrintType.Socket);
            return;
        }

        clients.add(client);
        util.Console.message("Client joined the room", Console.PrintType.Socket);

        if (clients.size() >= MAX_CLIENTS) {
            util.Console.warning("ROOM MAX_CLIENT_SIZE exceeded!", Console.PrintType.Socket);
            util.Console.message("Closing the room", Console.PrintType.Socket);
            open = false;
            util.Console.message("Room closed", Console.PrintType.Socket);
            util.Console.message("Creating room thread", Console.PrintType.Socket);
            Thread roomThread = new Thread(this);
            util.Console.message("Starting room thread", Console.PrintType.Socket);
            roomThread.start();
        }
    }

    public ServerRoom() {
        util.Console.message("New Server Room created", Console.PrintType.Socket);
    }

    public static util.events.ArgEvent<ServerRoom> onRoomStarted = new ArgEvent<>();

    @Override
    public void run() {
        inProgress = true;

        util.Console.message("Room process started", Console.PrintType.Socket);

        onRoomStarted.run(this);

        util.Console.message("Room process finished", Console.PrintType.Socket);
        inProgress = false;
    }
}