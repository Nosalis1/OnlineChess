import audio.AudioManager;
import game.GameManager;
import gui.GuiManager;
import socket.NetworkManager;
import socket.RoomManager;
import util.Console;

public class Main {
    public static void main(String[] args) {
        if (NetworkManager.isMaster()) {
            //Initializing Server
            util.Console.message("No active Master Server.Initializing Master Server.", Console.PrintType.Main);
            RoomManager.initialize();
            NetworkManager.initialize();
        } else {
            //Initializing Client
            util.Console.message("Initializing Client.", Console.PrintType.Main);
            AudioManager.initialize();
            GameManager.initialize();

            GuiManager.initialize();
        }
    }
}