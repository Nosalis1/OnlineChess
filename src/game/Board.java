package game;

import networking.LocalClient;
import networking.packageSystem.Packet;
import networking.packageSystem.PacketType;
import utility.math.Array;
import utility.math.Vector;
import utility.eventSystem.ArgumentEvent;
import utility.eventSystem.Event;

/**
 * The Board class represents the game board for a chess game.
 * It manages the state of the board, the pieces, and the game logic.
 */
public class Board {

    public static final Board instance = new Board();

    public static final int SIZE = 8;
    public static final int LAST = SIZE - 1;

    private Piece[][] pieces = new Piece[SIZE][SIZE];

    /**
     * Creates a deep copy of the pieces array.
     *
     * @return A copy of the pieces array.
     */
    private Piece[][] getPiecesCopy() {
        Piece[][] copy = new Piece[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Piece piece = pieces[i][j];
                copy[i][j] = piece == null ? null : new Piece(
                        piece.getColor(), piece.getType(), new Vector(i, j)
                );
            }
        }
        return copy;
    }

    /**
     * Constructs a new Board instance.
     */
    public Board() {
        this.data = new BoardData(this);
        this.data.updateElements();
    }

    /**
     * Constructs a new Board instance by copying another Board.
     *
     * @param other The Board instance to copy.
     */
    public Board(Board other) {
        this.pieces = other.getPiecesCopy();

        this.data = new BoardData(this);
        this.data.updateElements();

        this.onMove.clear();
        this.onMoved.clear();
        this.onMoveDone.clear();

        this.onCapture.clear();
        this.onCaptured.clear();

        this.onCheck.clear();
        this.onCheckMate.clear();

        this.onPromotion.clear();
        this.onPiecePromoted.clear();
        this.onPiecePromotion.clear();

        this.whiteTurn = other.whiteTurn;
    }

    /**
     * Clears the board by setting all positions to null.
     */
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                pieces[i][j] = null;
            }
        }
        this.data.resetElements();
    }

    /**
     * Adds a piece to the board at the specified position.
     *
     * @param newPiece The piece to add.
     */
    private void addPiece(final Piece newPiece) {
        final Vector position = newPiece.getPosition();
        pieces[position.x][position.y] = newPiece;
    }

    /**
     * Resets the board to the starting position of a chess game.
     */
    public void reset() {
        clear();

        final int[] order = {1, 2, 3, 4, 5, 3, 2, 1};
        for (int i = 0; i < SIZE; i++) {
            // Setup white pieces
            addPiece(new Piece(Piece.Color.White, Piece.Type.fromCode(order[i]), new Vector(0, i)));
            addPiece(new Piece(Piece.Color.White, Piece.Type.Pawn, new Vector(1, i)));

            // Setup black pieces
            addPiece(new Piece(Piece.Color.Black, Piece.Type.fromCode(order[i]), new Vector(LAST, i)));
            addPiece(new Piece(Piece.Color.Black, Piece.Type.Pawn, new Vector(LAST - 1, i)));
        }

        this.data.updateElements();
    }

    /**
     * Retrieves the piece at the specified position on the board.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return The piece at the specified position.
     */
    public Piece get(final int x, final int y) {
        return pieces[x][y];
    }

    /**
     * Retrieves the piece at the specified position on the board.
     *
     * @param position The position of the piece.
     * @return The piece at the specified position.
     */
    public Piece get(final Vector position) {
        return get(position.x, position.y);
    }

    /**
     * Retrieves a piece of the specified color and type with an offset.
     *
     * @param color  The color of the piece.
     * @param type   The type of the piece.
     * @param offset The offset to retrieve multiple pieces of the same type.
     * @return The piece with the specified color, type, and offset.
     */
    public Piece get(final Piece.Color color, final Piece.Type type, final int offset) {
        int off = offset;

        Array<Piece> currentPieces = color == Piece.Color.White ? data.white.getPieces() : data.black.getPieces();

        for (int i = 0; i < currentPieces.size(); i++) {
            if (currentPieces.get(i).isType(type)) {
                if (off <= 0)
                    return currentPieces.get(i);
                else
                    off--;
            }
        }
        return null;
    }

    /**
     * Retrieves a piece of the specified color and type.
     *
     * @param color The color of the piece.
     * @param type  The type of the piece.
     * @return The piece with the specified color and type.
     */
    public Piece get(final Piece.Color color, final Piece.Type type) {
        return get(color, type, 0);
    }

    /**
     * Checks if the position at the specified coordinates on the board is null (no piece).
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return {@code true} if the position is null, {@code false} otherwise.
     */
    public boolean isNull(final int x, final int y) {
        return get(x, y) == null;
    }

    /**
     * Checks if the position at the specified vector coordinates on the board is null (no piece).
     *
     * @param position The position to check.
     * @return {@code true} if the position is null, {@code false} otherwise.
     */
    public boolean isNull(final Vector position) {
        return isNull(position.x, position.y);
    }

    /**
     * Checks if there is a clear path between two positions on the board.
     *
     * @param x   The x-coordinate of the starting position.
     * @param y   The y-coordinate of the starting position.
     * @param dx  The x-coordinate of the destination position.
     * @param dy  The y-coordinate of the destination position.
     * @param ddx The change in x-coordinate for each step in the path.
     * @param ddy The change in y-coordinate for each step in the path.
     * @return True if there is a clear path between the positions, false otherwise.
     */
    public boolean inPath(final int x, final int y, final int dx, final int dy, int ddx, int ddy) {
        Vector i = new Vector(x, y);
        i.add(ddx, ddy);

        while (i.x != dx || i.y != dy) {
            if (!isNull(i))
                return false;

            i.add(ddx, ddy);
        }

        return true;
    }

    /**
     * Checks if there is a clear path between two positions on the board.
     *
     * @param from The starting position.
     * @param dx   The x-coordinate of the destination position.
     * @param dy   The y-coordinate of the destination position.
     * @param ddx  The change in x-coordinate for each step in the path.
     * @param ddy  The change in y-coordinate for each step in the path.
     * @return True if there is a clear path between the positions, false otherwise.
     */
    public boolean inPath(final Vector from, final int dx, final int dy, int ddx, int ddy) {
        return inPath(from.x, from.y, dx, dy, ddx, ddy);
    }

    /**
     * Checks if there is a clear path between two positions on the board.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @param ddx  The change in x-coordinate for each step in the path.
     * @param ddy  The change in y-coordinate for each step in the path.
     * @return True if there is a clear path between the positions, false otherwise.
     */
    public boolean inPath(final Vector from, final Vector to, int ddx, int ddy) {
        return inPath(from, to.x, to.y, ddx, ddy);
    }

    /**
     * Checks if there is a clear path between two positions on the board.
     *
     * @param from The starting position.
     * @param to   The destination position.
     * @param step The step vector indicating the change in coordinates for each step in the path.
     * @return True if there is a clear path between the positions, false otherwise.
     */
    @SuppressWarnings("unused")
    public boolean inPath(final Vector from, final Vector to, Vector step) {
        return inPath(from, to, step.x, step.y);
    }

    private boolean whiteTurn = true;

    /**
     * Checks if it is currently white's turn.
     *
     * @return True if it is white's turn, false if it is black's turn.
     */
    public final boolean isWhiteTurn() {
        return this.whiteTurn;
    }

    /**
     * Skips the turn, changing the current turn from white to black or from black to white.
     */
    private void skipTurn() {
        this.whiteTurn = !this.whiteTurn;
    }

    private final BoardData data;

    /**
     * Gets the BoardData associated with the board.
     *
     * @return The BoardData object.
     */
    public final BoardData getData() {
        return this.data;
    }

    /**
     * Event triggered when a capture occurs.
     */
    public Event onCapture = new Event();

    /**
     * Event triggered when a piece is captured.
     */
    public ArgumentEvent<Piece> onCaptured = new ArgumentEvent<>();

    /**
     * Event triggered when a move occurs.
     */
    public Event onMove = new Event();

    /**
     * Event triggered when a piece is moved.
     */
    public ArgumentEvent<Piece> onMoved = new ArgumentEvent<>();

    /**
     * Event triggered when a move is completed.
     */
    public ArgumentEvent<Move> onMoveDone = new ArgumentEvent<>();

    /**
     * Event triggered when a check is detected for a specific color.
     */
    public ArgumentEvent<Piece.Color> onCheck = new ArgumentEvent<>();

    /**
     * Event triggered when a checkmate is detected for a specific color.
     */
    public ArgumentEvent<Piece.Color> onCheckMate = new ArgumentEvent<>();

    /**
     * Event triggered when a promotion occurs.
     */
    public Event onPromotion = new Event();

    /**
     * Event triggered when a piece is promoted.
     */
    public ArgumentEvent<Piece> onPiecePromotion = new ArgumentEvent<>();

    /**
     * Event triggered when a piece is successfully promoted.
     */
    public ArgumentEvent<Piece> onPiecePromoted = new ArgumentEvent<>();

    /**
     * Moves a piece from the specified source position to the target position.
     *
     * @param from The source position of the piece.
     * @param to   The target position to move the piece to.
     */
    public void move(final Vector from, final Vector to) {
        onMove.run();

        Piece temp = get(from);

        if (!isNull(to)) {
            onCapture.run();
            onCaptured.run(this.get(to));
        }

        pieces[from.x][from.y] = null;
        pieces[to.x][to.y] = temp;

        temp.updatePosition(to);
        onMoved.run(temp);

        onMoveDone.run(new Move(from, to));

        if (!checkPromotion(temp))
            skipTurn();

        checkCheckMate();
    }

    /**
     * Moves a piece based on the specified Move object.
     *
     * @param move The Move object representing the piece movement.
     */
    public void move(final Move move) {
        move(move.getFrom(), move.getTo());
    }

    /**
     * Moves a piece from the specified source position to the target position in a networked environment.
     * Sends the move over the network.
     *
     * @param from The source position of the piece.
     * @param to   The target position to move the piece to.
     */
    public void networkMove(final Vector from, final Vector to) {
        Move move = new Move(from, to);

        // Send move over network
        LocalClient.instance.send(new Packet(PacketType.MOVE, move.pack()));

        move(move);
    }

    /**
     * Moves a piece based on the specified Move object in a networked environment.
     * Sends the move over the network.
     *
     * @param move The Move object representing the piece movement.
     */
    @SuppressWarnings("unused")
    public void networkMove(final Move move) {
        networkMove(move.getFrom(), move.getTo());
    }

    /**
     * Attempts to move a piece from the specified source position to the target position.
     * If the move is valid, it triggers a network move and returns true. Otherwise, it returns false.
     *
     * @param from The source position of the piece.
     * @param to   The target position to move the piece to.
     * @return True if the move is valid and triggered, false otherwise.
     */
    public boolean tryMove(final Vector from, final Vector to) {
        Piece temp = get(from);

        if (temp.canMove(to)) {
            networkMove(from, to);
            return true;
        }
        return false;
    }

    /**
     * Attempts to move a piece based on the given Move object.
     * If the move is valid, it calls the tryMove method with the source and target positions of the move.
     *
     * @param move The Move object representing the piece movement.
     * @return True if the move is valid and triggered, false otherwise.
     */
    @SuppressWarnings("unused")
    public boolean tryMove(final Move move) {
        return tryMove(move.getFrom(), move.getTo());
    }

    /**
     * Checks if the white and black kings are in a check position.
     * Updates the `inCheck` flag for both players accordingly.
     * If a king is in check, it triggers the `onCheck` event for the corresponding player.
     */
    private void checkCheck() {
        Piece king = get(Piece.Color.White, Piece.Type.King);
        data.white.setInCheck(false);

        if (king == null)
            data.white.setInCheck(true);
        else {
            Array<Piece> opponentPieces = data.black.getPieces();
            for (int i = 0; i < opponentPieces.size(); i++) {
                if (opponentPieces.get(i).canMove(king.getPosition())) {
                    data.white.setInCheck(true);
                    break;
                }
            }
        }

        if (data.white.isInCheck())
            onCheck.run(Piece.Color.White);

        king = get(Piece.Color.Black, Piece.Type.King);
        data.black.setInCheck(false);

        if (king == null)
            data.black.setInCheck(true);
        else {
            Array<Piece> opponentPieces = data.white.getPieces();
            for (int i = 0; i < opponentPieces.size(); i++) {
                if (opponentPieces.get(i).canMove(king.getPosition())) {
                    data.black.setInCheck(true);
                    break;
                }
            }
        }

        if (data.black.isInCheck())
            onCheck.run(Piece.Color.Black);
    }

    /**
     * Checks if either player is in a checkmate position.
     * It calls the checkCheck method to update the check status first.
     * If a player is in checkmate, it triggers the `onCheckMate` event for the corresponding player.
     */
    private void checkCheckMate() {
        checkCheck();

        if (instance != this)
            return;

        Array<Piece> tempPieces;

        if (data.white.isInCheck()) {
            data.white.setInCheckMate(true);

            tempPieces = data.white.getPieces();
            Array<Move> legalMoves = new Array<>();
            tempPieces.forEach((Piece piece) -> {
                Array<Vector> moves = piece.getMoves();
                moves.forEach((Vector destination) -> legalMoves.add(new Move(piece.getPosition(), destination)));
            });

            for (int i = 0; i < legalMoves.size(); i++) {
                Move move = legalMoves.get(i);
                Board tempBoard = new Board(this);

                tempBoard.move(move);

                if (!tempBoard.data.white.isInCheck()) {
                    data.white.setInCheckMate(false);
                    break;
                }
            }

            if (data.white.isInCheckMate())
                onCheckMate.run(Piece.Color.White);
        }

        if (data.black.isInCheck()) {
            data.black.setInCheckMate(true);

            tempPieces = data.black.getPieces();
            Array<Move> legalMoves = new Array<>();
            tempPieces.forEach((Piece piece) -> {
                Array<Vector> moves = piece.getMoves();
                moves.forEach((Vector destination) -> legalMoves.add(new Move(piece.getPosition(), destination)));
            });

            for (int i = 0; i < legalMoves.size(); i++) {
                Move move = legalMoves.get(i);
                Board tempBoard = new Board(this);

                tempBoard.move(move);

                if (!tempBoard.data.black.isInCheck()) {
                    data.black.setInCheckMate(false);
                    break;
                }
            }

            if (data.black.isInCheckMate())
                onCheckMate.run(Piece.Color.Black);
        }
    }

    /**
     * Checks if the given piece is eligible for promotion and triggers the promotion process if necessary.
     * If the piece is a pawn and reaches the last row (x == 0 or x == LAST), it triggers the `onPromotion` and `onPiecePromotion` events.
     * If the local user is unable to play, it sends a custom packet over the network.
     * Returns true if the promotion process is triggered, false otherwise.
     */
    private boolean checkPromotion(Piece piece) {
        if (piece == null || !piece.isType(Piece.Type.Pawn))
            return false;

        final int x = piece.getPosition().x;
        if (x == 0 || x == LAST) {
            onPromotion.run();
            onPiecePromotion.run(piece);
            if (!GameManager.localUser.canPlay())
                LocalClient.instance.send(new Packet(PacketType.CUSTOM));
            return true;
        }

        return false;
    }

    /**
     * Promotes the given piece to the specified type.
     * Triggers the `onPiecePromoted` event and skips the turn.
     */
    public void promotePiece(Piece piece, Piece.Type toType) {
        piece.promote(toType);
        onPiecePromoted.run(piece);
        skipTurn();
    }

    /**
     * Promotes the given piece to the specified type and sends the change over the network.
     * It constructs a packet with the position and new type information, then sends it over the network.
     * Finally, it calls the `promotePiece` method to perform the local promotion process.
     */
    public void networkPromotePiece(Piece piece, Piece.Type toType) {
        Vector position = piece.getPosition();
        String buffer = position.pack();
        buffer += "~" + toType.getCode();

        // Send change over network
        LocalClient.instance.send(new Packet(PacketType.CHANGE_TYPE, buffer));

        promotePiece(piece, toType);
    }
}