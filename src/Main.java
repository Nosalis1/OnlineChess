import audio.AudioManager;
import game.GameManager;
import gui.GuiManager;
import socket.NetworkManager;
import socket.RoomManager;

public class Main {
    public static void main(String[] args) {
        if (NetworkManager.isMaster()) {
            //Initializing Server
            RoomManager.initialize();
            NetworkManager.initialize();
        } else {
            //Initializing Client
            AudioManager.initialize();
            GameManager.initialize();

            GuiManager.initialize();
        }
    }
}