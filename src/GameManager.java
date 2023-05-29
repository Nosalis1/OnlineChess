import game.Board;
import game.Piece;
import gui.ColorGradient;
import gui.Game;
import gui.images.Field;
import gui.images.Image;
import util.Array;
import util.Vector;

public class GameManager {
    public static GameManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new GameManager();
    }

    public GameManager() {
        Field.onFieldClicked.add(this::onFieldClicked);

        Board.onPieceEaten.add(this::onPieceEaten);
        Board.onPieceWillMove.add(this::onPieceWillMove);
        Board.onPieceMoved.add(this::onPieceMoved);

        Board.reset();
        updateAll();
    }

    public void updateAll() {
        util.Array<Piece> allPieces = Board.getAllPieces();

        allPieces.foreach((Piece piece) -> {
            util.Vector at = piece.getPosition();

            Game.instance.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
        });
    }

    private void onPieceEaten(Piece piece) {
        util.Vector at = piece.getPosition();

        Game.instance.getField(at).setImage(null);
    }

    private void onPieceWillMove(Piece piece) {
        util.Vector at = piece.getPosition();

        Game.instance.getField(at).setImage(null);
    }

    private void onPieceMoved(Piece piece) {
        util.Vector at = piece.getPosition();

        Game.instance.getField(at).setImage(Image.IMAGES[piece.getColorCode()][piece.getTypeCode() - 1]);
    }

    private Array<Field> currentHighlights = new Array<>();

    private void resetHighlights() {
        currentHighlights.foreach((Field field) -> {
            field.setColor(ColorGradient.FIELD.getColor(field.isGradient()));
        });
        currentHighlights.clear();
    }

    private void setSelected(Vector at) {
        resetHighlights();

        Field temp = Game.instance.getField(at);
        temp.setColor(ColorGradient.HIGHLIGHT.getColor(temp.isGradient()));
        currentHighlights.add(temp);

        setMoves(at);
    }

    private void setMoves(Vector at) {
        Piece piece = Board.get(at);
        if (piece == null)
            return;

        Array<Vector> moves = piece.getMoves();

        moves.foreach((Vector position) -> {
            Field temp = Game.instance.getField(position);

            temp.setColor(ColorGradient.MOVE.getColor(temp.isGradient()));
            currentHighlights.add(temp);
        });
    }

    Vector selected;

    private void handleClicked(Vector at) {
        if (selected == null && !Board.isNull(at)) {
            selected = at;
            setSelected(at);
        } else if (selected == at) {
            selected = null;
            resetHighlights();
        } else if (selected != null) {
            Board.tryMove(selected, at);
            selected = null;
            resetHighlights();
        }
    }

    private void onFieldClicked(Vector at) {
        handleClicked(at);
    }
}