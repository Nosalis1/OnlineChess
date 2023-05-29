import gui.Game;
import util.Array;

public class Main {
    public static void main(String[] args) {

        Game.instance.showWindow();
        if (true)
            return;
        util.Array<String> someArray = new Array<>();
        someArray.foreach(System.out::println);

        util.Console.message("Hello World!");

        RoomManager.initialize();
        if (!NetworkManager.initialize()) {
            try {
                AudioManager.initialize();
                game.users.User.loadUsers();
                gui.Login.instance.showWindow();
            } catch (Exception e) {
                util.Console.error("Error while initializing in main method: " + e.getMessage());
                System.exit(-1);
            }
        }
    }
}