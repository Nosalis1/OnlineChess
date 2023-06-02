import audio.AudioManager;
import game.GameManager;
import gui.GuiManager;
import gui.Login;
import gui.Menu;
import socket.NetworkManager;
import socket.RoomManager;
import util.Array;
import util.Console;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (NetworkManager.isMaster()) {
            //Initializing Server
            util.Console.message("No active Master Server.Initializing Master Server.",null);
            RoomManager.initialize();
            NetworkManager.initialize();
        } else {
            //Initializing Client
            util.Console.message("Initializing Client.", null);
            AudioManager.initialize();
            GameManager.initialize();

            GuiManager.initialize();
        }
    }
}