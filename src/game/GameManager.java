package game;

import game.users.User;
import gui.ChosePiece;
import gui.GuiManager;
import gui.images.Field;
import networking.LocalClient;
import networking.packageSystem.Packet;
import networking.packageSystem.PacketType;
import utility.math.Vector;
import utility.eventSystem.Event;

import java.io.IOException;

@SuppressWarnings("unused")
public abstract class GameManager {
    private static boolean initialized = false;

    public static boolean isInitialized() {
        return initialized;
    }

    public static void initialize() {
        if (initialized)
            return;

        try {
            User.loadUsers();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Field.onFieldClicked.add(GameManager::onFieldClicked);

        Board.instance.onCheck.add((Piece.Color color) -> System.out.println(color.toString() + " in check!"));
        Board.instance.onCheckMate.add((Piece.Color color) -> {
            System.out.println(color.toString() + " in checkMate!");
            onGameEnded.run();
        });

        Board.instance.onMoved.add((Piece piece) -> lastMovedPiece = piece);
        ChosePiece.onTypeSelected.add((Piece.Type newType) -> {
            if (lastMovedPiece == null)
                return;
            Board.instance.networkPromotePiece(lastMovedPiece, newType);
            GuiManager.updateField(lastMovedPiece.getPosition());
        });

        Board.instance.onPromotion.addAction(() -> {
            if (localUser.canPlay()) {
                GuiManager.getGameWindow().setEnabled(false);
                GuiManager.getChosePieceWindow().showWindow();
            }
        });

        initialized = true;
    }

    public static utility.eventSystem.Event onGameStarted = new Event();
    public static utility.eventSystem.Event onGameEnded = new Event();

    public static void newGame() {
        Board.instance.reset();
        onGameStarted.run();
    }

    public static User localUser = null;
    public static User opponent = null;

    public static User getWinner() {
        BoardData data = Board.instance.getData();

        if (data.white.isInCheckMate()) {
            return localUser.isWhite() ? localUser : opponent;
        } else if (data.black.isInCheckMate()) {
            return !localUser.isWhite() ? localUser : opponent;
        } else
            return null;
    }

    public static boolean isLocalWinner() {
        return localUser == getWinner();
    }

    private static Piece lastMovedPiece = null;

    static Vector selected;

    private static void onFieldClicked(Vector at) {
        if (!localUser.canPlay())
            return;
        if (selected == null && (!Board.instance.isNull(at) && Board.instance.get(at).isColor(localUser.isWhite() ? Piece.Color.White : Piece.Color.Black))) {
            selected = at;
            GuiManager.onFieldClicked(at);
        } else if (selected == at) {
            selected = null;
            GuiManager.resetHighlights();
        } else if (selected != null) {
            Board.instance.tryMove(selected, at);
            selected = null;
            GuiManager.resetHighlights();
        }
    }

    public static void handleNetworkPackage(final Packet packet) {

        if (packet == null)
            return;

        PacketType type = PacketType.fromCode(packet.getPackedBuffer());

        String[] values;
        switch (type) {
            case MOVE:
                Move move = new Move(new Vector(), new Vector());
                move.unpack(packet.getBufferData());
                Board.instance.move(move);
                break;
            case CHANGE_TYPE:
                Vector at = new Vector();
                Piece.Type newType;
                values = packet.getBufferData().split("~");

                if (values.length == 2) {
                    try {
                        at.unpack(values[0]);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    newType = Piece.Type.fromCode(Integer.parseInt(values[1]));
                } else
                    throw new IllegalArgumentException("Invalid buffer format");

                Board.instance.promotePiece(Board.instance.get(at), newType);
                GuiManager.updateField(at);
                break;
            case CUSTOM:
                break;
            case SEND_PLAYER:
                LocalClient.instance.send(new Packet(PacketType.PLAYER, localUser.pack()));
                break;
            case PLAYER:
                opponent = new User(packet.getBufferData());
                opponent.setWhite(!localUser.isWhite());
                break;
            case START_GAME:
                newGame();
                break;
            case CHANGE_COLOR:
                localUser.changeSide();
                break;
            case DISCONNECT:
                break;
            default:
                break;
        }
    }
}