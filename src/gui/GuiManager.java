package gui;

import game.Board;
import game.GameManager;
import game.Move;
import game.Piece;
import gui.images.Field;
import gui.images.Image;
import util.Array;
import util.ColorGradient;
import util.Vector;
import util.events.Event;

import java.awt.*;

public abstract class GuiManager {
    public static final util.events.Event onButtonClick = new Event();

    public static void initialize() {
        Image.wakeUp();

        loginWindow.showWindow();

        Board.instance.onCaptured.add((Piece piece) -> {
            gameWindow.getField(piece.getPosition()).setImage(null);
        });
        Board.instance.onMoveDone.add((Move move) -> {
            Piece piece = Board.instance.get(move.getTo());
            gameWindow.getField(move.getFrom()).setImage(null);
            gameWindow.getField(move.getTo()).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
        });

        GameManager.onGameStarted.add(() -> {
            updateFields();
            menuWindow.hideWindow();
            gameWindow.showWindow();
            gameWindow.updateInfoTable(null);
        });

        updateFields();
    }

    private static final Login loginWindow = new Login();
    private static final Register registerWindow = new Register();
    private static final Menu menuWindow = new Menu();
    private static final Game gameWindow = new Game();
    private static final ChosePiece chosePieceWindow = new ChosePiece();

    public static Login getLoginWindow() {
        return loginWindow;
    }

    public static Register getRegisterWindow() {
        return registerWindow;
    }

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

        util.Array<Piece> allPieces = Board.instance.getData().getAllPieces();
        allPieces.foreach((Piece piece) -> {
            util.Vector at = piece.getPosition();

            gameWindow.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
        });
    }

    public static void updateField(final Vector at) {
        Field tempField = gameWindow.getField(at);

        if (Board.instance.isNull(at)) {
            tempField.setImage(null);
        } else {
            Piece tempPiece = Board.instance.get(at);
            tempField.setImage(Image.IMAGES[tempPiece.getColorCode()][tempPiece.getTypeCode() - 1]);
        }
    }

    public static void loggedIn() {
        loginWindow.hideWindow();
        menuWindow.showWindow();
    }

    public static void registered() {
        registerWindow.hideWindow();
        menuWindow.showWindow();
    }

    public static void accountDeleted() {
        menuWindow.hideWindow();
        menuWindow.showWindow();
    }

    //HIGHLIGHTING
    private static final Array<Field> currentHighlights = new Array<>();

    public static void resetHighlights() {
        currentHighlights.foreach((Field field) -> field.setColor(ColorGradient.FIELD.getColor(field.isGradient())));
        currentHighlights.clear();
    }

    private static void setHighlight(Vector at) {
        Field temp = gameWindow.getField(at);
        temp.setColor(ColorGradient.HIGHLIGHT.getColor(temp.isGradient()));
        currentHighlights.add(temp);
    }

    private static void setHighlights(util.Array<Vector> at) {
        at.foreach((Vector position) -> {
            Field temp = gameWindow.getField(position);

            temp.setColor(Board.instance.isNull(position) ? ColorGradient.MOVE.getColor(temp.isGradient()) : ColorGradient.ATTACK.getColor(temp.isGradient()));
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
