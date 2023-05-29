import game.Board;
import game.Piece;
import gui.Game;
import gui.images.Image;
import util.Vector;

public class GameManager {
    public static GameManager instance;

    public static void initialize() {
        if (instance == null)
            instance = new GameManager();
    }

    public GameManager() {
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
}
