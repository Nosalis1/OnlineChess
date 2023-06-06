import audio.AudioManager;
import game.GameManager;
import gui.GuiManager;
import socket.NetworkManager;
import socket.RoomManager;
import util.Vector;

public class Main {
    public static void main(String[] args) {
        if (NetworkManager.isMaster()) {
            //Initializing Server
            util.Console.message("No active Master Server.Initializing Master Server.");
            RoomManager.initialize();
            NetworkManager.initialize();
        } else {
            //Initializing Client
            util.Console.message("Initializing Client.");
            AudioManager.initialize();
            GameManager.initialize();
            GuiManager.initialize();
        }
    }
}