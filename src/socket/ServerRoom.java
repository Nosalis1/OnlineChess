package socket;

import util.Array;
import util.events.ArgEvent;

public class ServerRoom implements Runnable {
    public static final int MAX_CLIENTS = 2;

    private final util.Array<ServerClient> clients = new Array<>();

    public util.Array<ServerClient> getClients() {
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

    public void clientTryJoin(ServerClient client) {
        if (!isOpen()) {
            return;
        }

        if (clients.contains(client)) {
            return;
        }

        clients.add(client);

        if (clients.size() >= MAX_CLIENTS) {
            open = false;
            Thread roomThread = new Thread(this);
            roomThread.start();
        }
    }

    public ServerRoom() {
    }

    public static util.events.ArgEvent<ServerRoom> onRoomStarted = new ArgEvent<>();

    @Override
    public void run() {
        if (inProgress)
            return;
        inProgress = true;

        onRoomStarted.run(this);
    }
}
