package game.users;

import util.Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class User {
    public static User currentUser = null;

    private static final util.Array<User> existingUsers = new Array<>();

    public static boolean login(String userName,String password) {
        for (int i = 0; i < existingUsers.size(); i++) {
            User user = existingUsers.get(i);
            if (user.getUserName().equals(userName) && user.isPassword(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public static void loadUsers() {
        final String DATA_PATH = "src/game/users/UserData";

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (line.equals(USER_TOKEN))
                    loadUser(reader);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static final String USER_TOKEN = "User:";
    private static final String USERNAME_TOKEN = "UserName: ";
    private static final String PASSWORD_TOKEN = "Password: ";

    //TODO: HANDLE EXCEPTIONS (VALIDATE DATA)
    private static void loadUser(BufferedReader reader) throws IOException {
        String line = reader.readLine(); //UserName

        String userName = line.substring(line.indexOf(USERNAME_TOKEN) + USERNAME_TOKEN.length());

        line = reader.readLine(); //Password

        String password = line.substring(line.indexOf(PASSWORD_TOKEN) + PASSWORD_TOKEN.length());

        existingUsers.add(new User(userName, password));
    }

    private final String userName;

    public final String getUserName() {
        return this.userName;
    }

    private final String password;

    public final boolean isPassword(String password) {
        return this.password.equals(password);
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}