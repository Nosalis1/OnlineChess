package socket.events;

import java.net.Socket;

public interface ServerEventable {
    public void onServerStarted();

    public void onServerStopped();

    public void onServerStartListening();

    public void onServerStopListening();

    public void onSocketConnected(Socket clientSocket);
}
