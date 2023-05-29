package game.users;

import util.Array;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class User {
    public static User currentUser = null;
    private static final com.google.gson.Gson gson = new com.google.gson.Gson();
    private static final util.Array<User> existingUsers = new Array<>();

    private final String userName;
    private final String password;
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static boolean login(String userName, String password) {
        for (int i = 0; i < existingUsers.size(); i++) {
            User user = existingUsers.get(i);
            if (user.getUserName().equals(userName) && user.isCorrectPassword(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public static void loadUsers() throws IOException {
        final String DATA_PATH = "src/game/users/UserData";

        try (FileReader fileReader = new FileReader(DATA_PATH)) {
            JsonObject json = gson.fromJson(fileReader, JsonObject.class);
            JsonArray usersArray = json.getAsJsonArray("users");

            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                String userName = userObject.get("username").getAsString();
                String passwordHash = userObject.get("password").getAsString();

                existingUsers.add(new User(userName, passwordHash));
            }
        }
    }

//    public static void removeUsers(String[] userNames) {
//        for (String userName : userNames)
//            removeUsers(userName);
//    }
//    public static void removeUsers(String userName) {
//
//    }
//    public static void addUsers(String[] userNames) {
//        List<String> unprocessedUsers = new ArrayList<>();
//        for (String userName : userNames) {
//            if (!existingUsers.contains())
//                unprocessedUsers.add(userName);
//            addUsers(userName);
//        }
//    }
//    public static void addUsers(String userName) {
//
//    }

    public final String getUserName() {
        return this.userName;
    }
    public final boolean isCorrectPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : md.digest())
                hexString.append(String.format("%02x", b));
            util.Console.message(hexString.toString());
            return this.password.equals(hexString.toString().toLowerCase());
        } catch (java.security.NoSuchAlgorithmException e) {
            util.Console.error("Set hashing algorithm in password comparing method doesn't exist!");
            return false;
        }
    }
}