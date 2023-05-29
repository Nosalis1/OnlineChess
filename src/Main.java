import util.Array;

public class Main {
    public static void main(String[] args) {

        util.Array<String> someArray = new Array<>();
        someArray.foreach((String item)->{
            System.out.println(item);
        });

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