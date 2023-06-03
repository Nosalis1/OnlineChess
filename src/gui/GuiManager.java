package gui;

import game.Board;
import game.Move;
import game.Piece;
import gui.images.Field;
import gui.images.Image;
import util.Array;
import util.ColorGradient;
import util.Vector;

public class GuiManager {
    public static GuiManager instance;

    public static void initialize() {
        Image.wakeUp();

        if (instance == null) instance = new GuiManager();
        util.Console.message("Initializing GuiManager.", instance);
    }

    private final Login loginWindow;
    private final Register registerWindow;
    private final Menu menuWindow;
    private final Game gameWindow;

    public Login getLoginWindow() {
        return this.loginWindow;
    }

    public Register getRegisterWindow() {
        return this.registerWindow;
    }

    public Menu getMenuWindow() {
        return this.menuWindow;
    }

    @SuppressWarnings("unused")
    public Game getGameWindow() {
        return this.gameWindow;
    }

    public GuiManager() {
        this.loginWindow = new Login();
        this.registerWindow = new Register();
        this.menuWindow = new Menu();
        this.gameWindow = new Game();

        this.loginWindow.showWindow();

        //Field.onFieldClicked.add(this::onFieldClicked);

        Board.instance.onPieceEaten.add(this::onPieceEaten);
        Board.instance.onPieceMoved.add(this::onPieceMoved);
        Board.instance.onPieceMove.add(this::onPieceMove);

        updateFields();
    }

    private void onPieceMove(Move move) {
        util.Vector at = move.getFrom();
        this.gameWindow.getField(at).setImage(null);
    }

    private void onPieceMoved(Piece piece) {
        util.Vector at = piece.getPosition();

        this.gameWindow.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
    }

    private void onPieceEaten(Piece piece) {
        util.Vector at = piece.getPosition();

        this.gameWindow.getField(at).setImage(null);
    }

    public void updateFields() {
        this.gameWindow.clearFields();

        util.Array<Piece> allPieces = Board.instance.getAllPieces();
        allPieces.foreach((Piece piece) -> {
            util.Vector at = piece.getPosition();

            this.gameWindow.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
        });
    }

    public void startGame() {
        updateFields();
        menuWindow.hideWindow();
        gameWindow.showWindow();
        gameWindow.updateInfoTable(null);
    }

    public void loggedIn() {
        this.loginWindow.hideWindow();
        this.menuWindow.showWindow();
    }

    public void registered() {
        this.registerWindow.hideWindow();
        this.menuWindow.showWindow();
    }

    public void accountDeleted() {
        this.getMenuWindow().hideWindow();
        this.getLoginWindow().showWindow();
    }

    //HIGHLIGHTING
    private final Array<Field> currentHighlights = new Array<>();

    public void resetHighlights() {
        currentHighlights.foreach((Field field) -> field.setColor(ColorGradient.FIELD.getColor(field.isGradient())));
        currentHighlights.clear();
    }

    private void setHighlight(Vector at) {
        Field temp = this.gameWindow.getField(at);
        temp.setColor(ColorGradient.HIGHLIGHT.getColor(temp.isGradient()));
        currentHighlights.add(temp);
    }

    private void setHighlights(util.Array<Vector> at) {
        at.foreach((Vector position) -> {
            Field temp = this.gameWindow.getField(position);

            temp.setColor(Board.instance.isNull(position) ? ColorGradient.MOVE.getColor(temp.isGradient()) : ColorGradient.ATTACK.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        });
    }

    //TODO:IMPLEMENT THIS LATER
    public void onFieldClicked(Vector vector) {
        resetHighlights();

        Piece piece = Board.instance.get(vector);

        if (piece == null) return;

        setHighlight(vector);
        setHighlights(piece.getMoves());
    }
}
