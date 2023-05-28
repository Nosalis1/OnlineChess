public class Main {
    public static void main(String[] args) {
        util.Console.message("Hello World!");

        // RoomManager.initialize();
        // NetworkManager.initialize();
        game.users.User.loadUsers();
        gui.Login.instance.showWindow();
    }
}