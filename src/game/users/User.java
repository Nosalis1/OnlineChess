package game.users;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import util.Array;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class User {
    public static User currentUser = null;
    private static final com.google.gson.Gson gson = new com.google.gson.Gson();
    private static final com.google.gson.Gson gsonBuilder = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
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

    private static final String DATA_PATH = "src/game/users/UserData";

    public static void loadUsers() throws IOException {
        existingUsers.clear();
        try (FileReader fileReader = new FileReader(DATA_PATH)) {
            JsonObject json = gson.fromJson(fileReader, JsonObject.class);
            JsonArray usersArray = json.getAsJsonArray("users");

            for (JsonElement userElement : usersArray) {
                JsonObject userObject = userElement.getAsJsonObject();
                String userName = userObject.get("username").getAsString();
                String password = userObject.get("password").getAsString();

                existingUsers.add(new User(userName, password));
            }
        }
    }

    public boolean addUser() throws IOException {
        loadUsers();
        for (int i = 0; i < existingUsers.size(); i++)
            if (existingUsers.get(i).equals(this)) return false;
        JsonArray usersArray;
        try (FileReader fileReader = new FileReader(DATA_PATH)) {
            JsonObject json = gson.fromJson(fileReader, JsonObject.class);
            usersArray = json.getAsJsonArray("users");

            JsonObject obj = new JsonObject();
            obj.addProperty("username", this.userName);
            obj.addProperty("password", this.password);
            usersArray.add(obj);
        } catch (Exception ignored) {
            return false;
        }
        try (FileWriter writer = new FileWriter(DATA_PATH)) {
            JsonObject json = new JsonObject();
            json.add("users", usersArray);
            writer.write(gsonBuilder.toJson(json));
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    public static boolean addUser(String userName, String password) throws IOException {
        return new User(userName, password).addUser();
    }

    public static boolean addUser(User user) throws IOException {
        return user.addUser();
    }

    public boolean removeUser() throws IOException {
        loadUsers();
        boolean userExist = false;
        for (int i = 0; i < existingUsers.size(); i++)
            if (existingUsers.get(i).equals(this)) {
                userExist = true;
                break;
            }
        if (!userExist) return false;
        JsonArray usersArray;
        try (FileReader fileReader = new FileReader(DATA_PATH)) {
            JsonObject json = gson.fromJson(fileReader, JsonObject.class);
            usersArray = json.getAsJsonArray("users");
        }
        for (JsonElement userElement : usersArray) {
            JsonObject userObject = userElement.getAsJsonObject();
            String userName = userObject.get("username").getAsString();
            String password = userObject.get("password").getAsString();
            if (userName.equals(this.userName) && password.equals(this.password)) {
                usersArray.remove(userObject);
                return true;
            }
        }
        try (FileWriter writer = new FileWriter(DATA_PATH)) {
            JsonObject json = new JsonObject();
            json.add("users", usersArray);
            writer.write(gsonBuilder.toJson(json));
        }
        return true;
    }

    public static boolean removeUser(String userName) throws IOException {
        User user = null;
        for (int i = 0; i < existingUsers.size(); i++) {
            User dummy = existingUsers.get(i);
            if (dummy.getUserName().equals(userName)) {
                user = dummy;
                break;
            }
        }
        if (user != null) return user.removeUser();
        return false;
    }

    public static boolean removeUser(User user) throws IOException {
        if (existingUsers.contains(user)) return user.removeUser();
        return false;
    }

    public final String getUserName() {
        return this.userName;
    }

    public final boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return String.format("User(%s, %s)", userName, password);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        User objUser = (User) obj;
        return this.userName.equals(objUser.getUserName()) && objUser.isCorrectPassword(this.password);
    }
}