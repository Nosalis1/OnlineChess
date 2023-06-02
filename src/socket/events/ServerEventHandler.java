package socket.events;

import util.Array;
import util.Console;

import java.net.Socket;

public class ServerEventHandler extends util.Array<ServerEventable> implements ServerEventable {

    @Override
    public void add(ServerEventable actions) {
        if (contains(actions)) {
            util.Console.warning("Action you are trying to add already exist to list!", this);
            return;
        }

        super.add(actions);
    }

    @Override
    public void onServerStarted() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStarted();
    }

    @Override
    public void onServerStopped() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStopped();
    }

    @Override
    public void onServerStartListening() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStartListening();
    }

    @Override
    public void onServerStopListening() {
        for (int i = 0; i < size(); i++)
            get(i).onServerStopListening();
    }

    @Override
    public void onSocketConnected(Socket clientSocket) {
        for (int i = 0; i < size(); i++)
            get(i).onSocketConnected(clientSocket);
    }
}
