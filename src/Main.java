import audio.AudioManager;
import game.GameManager;
import gui.GuiManager;
import networking.NetworkManager;
import networking.RoomManager;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World!");

        if (NetworkManager.isMaster()) {
            //Initializing Server
            utility.Console.message("No active Master Server.Initializing Master Server.");
            RoomManager.initialize();
            NetworkManager.initialize();
        } else {
            //Initializing Client
            utility.Console.message("Initializing Client.");
            AudioManager.initialize();
            GameManager.initialize();
            GuiManager.initialize();
        }
    }
}