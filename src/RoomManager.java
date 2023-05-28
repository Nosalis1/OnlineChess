import socket.ServerRoom;
import util.Array;

public class RoomManager {
    public static RoomManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new RoomManager();
    }

    public static final util.Array<ServerRoom> rooms = new Array<>();
    public static ServerRoom findOpenRoom() {
        for (int i = 0; i < rooms.size(); i++)
            if (rooms.get(i).isOpen())
                return rooms.get(i);
        return null;
    }
    public static ServerRoom createNewRoom() {
        ServerRoom newRoom = new ServerRoom();
        rooms.add(newRoom);
        return newRoom;
    }

    public RoomManager() {
        ServerRoom.onRoomStarted.add(this::onRoomStarted);
    }

    public void onRoomStarted(ServerRoom room) {
        handleRoomProcess(room);
    }

    private void handleRoomProcess(ServerRoom room){

        //TODO : DO GAME LOGIC

    }
}
