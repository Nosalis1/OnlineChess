package gui;

import game.*;
import game.users.User;
import gui.images.Field;
import gui.images.Image;
import utility.math.Array;
import utility.customGui.Colors;
import utility.math.Vector;
import utility.eventSystem.Event;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class GuiManager {
    public static final String DATA_PATH = "src\\gui\\images\\256px";

    public static final utility.eventSystem.Event onButtonClick = new Event();

    private static boolean initialized = false;

    @SuppressWarnings("unused")
    private static boolean isInitialized() {
        return initialized;
    }

    public static void initialize() {
        if (initialized)
            return;

        try {
            load();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        Board.instance.onCaptured.add((Piece piece) -> gameWindow.getField(piece.getPosition()).setImage(null));
        Board.instance.onMoveDone.add((Move move) -> {
            Piece piece = Board.instance.get(move.getTo());
            gameWindow.getField(move.getFrom()).setImage(null);
            gameWindow.getField(move.getTo()).setImage(pieceImages[piece.getColorCode()][piece.getTypeCode() - 1]);
            resetHighlights();
        });

        Board.instance.onCheck.add((Piece.Color ignore)-> resetHighlights());

        GameManager.onGameStarted.addAction(() -> {
            updateFields();
            menuWindow.hideWindow();
            gameWindow.showWindow();
            gameWindow.setEnabled(true);
            gameWindow.updateInfoTable(null);
        });

        GameManager.onGameEnded.addAction(()->{
            gameWindow.setEnabled(false);
            User winner = GameManager.getWinner();
            assert winner != null;
            JOptionPane.showMessageDialog(null,winner.getUserName()+" won the game!","Game Ended",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);//TODO: DISCONNECT & SHOW MENU WINDOW
        });

        loginWindow = new Login();
        registerWindow = new Register();
        menuWindow  = new Menu();
        gameWindow = new Game();
        chosePieceWindow = new ChosePiece();

        updateFields();
        loginWindow.showWindow();

        initialized = true;
    }

    private static final Array<Image> loadedImages = new Array<>();

    private static Image findImage(String name) {
        for (int i = 0; i < loadedImages.size(); i++) {
            if (loadedImages.get(i).isName(name))
                return loadedImages.get(i);
        }
        return null;
    }

    private static void load() throws FileNotFoundException {
        File file = new File(DATA_PATH);

        if (file.exists()) {
            String[] list = file.list();

            assert list != null;

            for (String item : list) {
                loadedImages.add(
                        new Image(file.getPath() + "\\" + item));
            }

            pieceImages = new Image[][]{
                    {
                            findImage("w_rook_png_shadow_256px.png"),
                            findImage("w_knight_png_shadow_256px.png"),
                            findImage("w_bishop_png_shadow_256px.png"),
                            findImage("w_queen_png_shadow_256px.png"),
                            findImage("w_king_png_shadow_256px.png"),
                            findImage("w_pawn_png_shadow_256px.png")
                    },
                    {
                            findImage("b_rook_png_shadow_256px.png"),
                            findImage("b_knight_png_shadow_256px.png"),
                            findImage("b_bishop_png_shadow_256px.png"),
                            findImage("b_queen_png_shadow_256px.png"),
                            findImage("b_king_png_shadow_256px.png"),
                            findImage("b_pawn_png_shadow_256px.png")
                    }
            };
        } else
            throw new FileNotFoundException();
    }

    public static Image[][] pieceImages = null;

    private static Login loginWindow;
    private static Register registerWindow;
    private static Menu menuWindow;
    private static Game gameWindow;
    private static ChosePiece chosePieceWindow;

    @SuppressWarnings("unused")
    public static Login getLoginWindow() {
        return loginWindow;
    }

    public static Register getRegisterWindow() {
        return registerWindow;
    }

    @SuppressWarnings("unused")
    public static Menu getMenuWindow() {
        return menuWindow;
    }

    public static Game getGameWindow() {
        return gameWindow;
    }

    @SuppressWarnings("unused")
    public static ChosePiece getChosePieceWindow() {
        return chosePieceWindow;
    }


    public static void updateFields() {
        gameWindow.clearFields();

        Array<Piece> allPieces = Board.instance.getData().getAllPieces();
        allPieces.forEach((Piece piece) -> {
            Vector at = piece.getPosition();

            gameWindow.getField(at).setImage(pieceImages[piece.getColorCode()][piece.getTypeCode() - 1]);
        });
    }

    public static void updateField(final Vector at) {
        Field tempField = gameWindow.getField(at);

        if (Board.instance.isNull(at)) {
            tempField.setImage(null);
        } else {
            Piece tempPiece = Board.instance.get(at);
            tempField.setImage(pieceImages[tempPiece.getColorCode()][tempPiece.getTypeCode() - 1]);
        }
    }

    public static void loggedIn() {
        loginWindow.hideWindow();
        menuWindow.setLocation(loginWindow.getLocation());
        menuWindow.showWindow();
    }

    public static void registered() {
        registerWindow.hideWindow();
        menuWindow.setLocation(registerWindow.getLocation());
        menuWindow.showWindow();
    }

    public static void accountDeleted() {
        menuWindow.hideWindow();
        menuWindow.showWindow();
    }

    //HIGHLIGHTING
    private static final Array<Field> currentHighlights = new Array<>();

    public static void resetHighlights() {
        currentHighlights.forEach((Field field) -> field.setColor(field.isGradient() ? Colors.FIELD.getLightColor() : Colors.FIELD.getDarkColor()));
        currentHighlights.clear();

        BoardData data = Board.instance.getData();
        if(data.white.isInCheck()){
            Field temp = gameWindow.getField(Board.instance.get(Piece.Color.White, Piece.Type.King).getPosition());
            temp.setColor(Colors.ATTACK.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        }
        if(data.black.isInCheck()) {
            Piece kingPiece = Board.instance.get(Piece.Color.Black, Piece.Type.King);
            if (kingPiece == null)
                return;
            Field temp = gameWindow.getField(kingPiece.getPosition());
            temp.setColor(Colors.ATTACK.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        }
        //TODO: WHEN PLAYER EXIT CHECK POSITION,FIELD IS STILL COLORED RED!
    }

    private static void setHighlight(Vector at) {
        Field temp = gameWindow.getField(at);
        temp.setColor(Colors.HIGHLIGHT.getColor(temp.isGradient()));
        currentHighlights.add(temp);
    }

    private static void setHighlights(Array<Vector> at) {
        at.forEach((Vector position) -> {
            Field temp = gameWindow.getField(position);

            temp.setColor(Board.instance.isNull(position) ? Colors.MOVE.getColor(temp.isGradient()) : Colors.ATTACK.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        });
    }

    public static void onFieldClicked(Vector vector) {
        resetHighlights();

        Piece piece = Board.instance.get(vector);

        if (piece == null) return;

        setHighlight(vector);
        setHighlights(piece.getMoves());
    }
}