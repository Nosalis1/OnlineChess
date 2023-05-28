public class Main {
    public static void main(String[] args) {

        util.Console.message("Hello World!");

        RoomManager.initialize();
        if (!NetworkManager.initialize()) {
            AudioManager.initialize();
            game.users.User.loadUsers();
            gui.Login.instance.showWindow();
        }
    }
}