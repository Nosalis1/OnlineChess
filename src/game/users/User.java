package game.users;

import util.Array;
import java.io.*;

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
        this.password = getMD5StringHash(password);
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

    private static final String DATA_PATH = "src/game/users/UserData";
    public static void loadUsers() throws IOException {
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

    public boolean addUser() throws IOException {
        if (!existingUsers.contains(this)) {
            try (FileReader fileReader = new FileReader(DATA_PATH)) {
                JsonObject json = gson.fromJson(fileReader, JsonObject.class);
                JsonArray usersArray = json.getAsJsonArray("users");

                JsonObject obj = new JsonObject();
                obj.addProperty("username", this.userName);
                obj.addProperty("password", this.password);
                usersArray.add(obj);
            }
            return true;
        }
        return false;
    }
    public static boolean addUser(String userName, String password) throws IOException {
        User user = new User(userName, getMD5StringHash(password));
        if (!existingUsers.contains(user))
            return user.addUser();
        return false;
    }
    public static boolean addUser(User user) throws IOException {
        if (!existingUsers.contains(user))
            return user.addUser();
        return false;
    }

    public boolean removeUser() throws IOException {
        if (existingUsers.contains(this)) {
            JsonArray usersArray;
            try (FileReader fileReader = new FileReader(DATA_PATH)) {
                JsonObject json = gson.fromJson(fileReader, JsonObject.class);
                usersArray = json.getAsJsonArray("users");
            }
            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                String userName = userObject.get("username").getAsString();
                String passwordHash = userObject.get("password").getAsString();
                if (userName.equals(this.userName) && passwordHash.equals(this.password)) {
                    usersArray.remove(userObject);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean removeUser(String userName) throws IOException {
        boolean userExists = false;
        User user = null;
        for (User dummy : existingUsers.getArray())
            if (dummy.getUserName().equals(userName)) {
                userExists = true;
                user = dummy;
                break;
            }
        if (userExists)
            return user.removeUser();
        return false;
    }
    public static boolean removeUser(User user) throws IOException {
        if (existingUsers.contains(user))
            return user.removeUser();
        return false;
    }

    public final String getUserName() {
        return this.userName;
    }
    public final boolean isCorrectPassword(String password) {
        return this.password.equals(getMD5StringHash(password));
    }

    private static String getMD5StringHash(String inputString) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(inputString.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : md.digest())
                hexString.append(String.format("%02x", b));
            return hexString.toString().toLowerCase();
        } catch (java.security.NoSuchAlgorithmException e) {
            util.Console.error("Set hashing algorithm in User class doesn't exist!");
            return "";
        }
    }
}