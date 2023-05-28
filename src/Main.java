import game.users.User;
import util.events.ArgEvent;
import util.events.Event;

public class Main {
    public static void main(String[] args) {

        util.Console.message("Hello World!");

        RoomManager.initialize();
        if (!NetworkManager.initialize()) {
            game.users.User.loadUsers();
            gui.Login.instance.showWindow();
        }
    }
}