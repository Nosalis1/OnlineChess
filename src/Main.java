import audio.AudioManager;
import game.GameManager;
import gui.Menu;

public class Main {
    public static void main(String[] args) {

        RoomManager.initialize();
        if (!NetworkManager.initialize()) {
            try {
                AudioManager.initialize();
                GameManager.initialize();//TODO:MOVE IN INITIALIZERS

                Menu.instance.showWindow();
                //game.users.User.loadUsers();
                //gui.Login.instance.showWindow();//TODO:ENABLE LATER
            } catch (Exception e) {
                util.Console.error("Error while initializing in main method: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}