package gui;

import com.sun.tools.jconsole.JConsoleContext;
import game.Board;
import game.GameManager;
import game.Piece;
import gui.images.Field;
import util.Array;
import util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Game extends Window {
    public static Game instance;

    public Game() {
        super("Game");
        super.setSize(1100, 860);
        super.setLayout(null);
        super.setLocationRelativeTo(null);

        createTable();
        createInfo();
    }

    public static void initialize() {
        if (instance == null)
            instance = new Game();
    }

    gui.images.Field[][] fields = null;

    public gui.images.Field[][] getFields() {
        return this.fields;
    }

    public gui.images.Field getField(final int x,final int y) {
        return this.fields[x][y];
    }

    public gui.images.Field getField(final Vector at) {
        return getField(at.X, at.Y);
    }

    private void createTable() {
        JPanel tablePanel = new JPanel();

        tablePanel.setLayout(new GridLayout(Board.SIZE, Board.SIZE));
        tablePanel.setBounds(10, 10, 800, 800);
        tablePanel.setName("table");

        fields = new Field[Board.SIZE][Board.SIZE];

        boolean ws = false;
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                fields[i][j] = new Field(new Vector(i, j), new Vector(100, 100), ws = !ws);
                tablePanel.add(fields[i][j]);
            }
            ws = !ws;
        }

        add(tablePanel);
    }

    public void updateInfoBoards() {

    }

    private JPanel getPlayerInfoPanel(boolean isWhite) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(isWhite ? Color.LIGHT_GRAY : Color.GRAY, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelPlayer = new JLabel("Player info", SwingConstants.CENTER);
        panel.setPreferredSize(new Dimension(260, labelPlayer.getPreferredSize().height));
        panel.setFont(labelPlayer.getFont().deriveFont(Font.BOLD, 20F));
        panel.add(labelPlayer);
        panel.add(new JLabel());
        return panel;
    }

    public void updateInfoTable(Piece piece) {
        int pawnsWhite = 0, pawnsBlack = 0,
            rooksWhite = 0, rooksBlack = 0,
            knightsWhite = 0, knightsBlack = 0,
            bishopsWhite = 0, bishopsBlack = 0,
            queenWhite = 0, queenBlack = 0,
            kingWhite = 0, kingBlack = 0;

        util.Array<Piece> piecesWhite = Board.getWhitePieces(),
                          piecesBlack = Board.getBlackPieces();
        for (int i=0; i<16; i++) {
            switch (piecesWhite.get(i).getType()) {
                case Pawn -> pawnsWhite++;
                case Rook -> rooksWhite++;
                case Knight -> knightsWhite++;
                case Bishop -> bishopsWhite++;
                case Queen -> queenWhite = 1;
                case King -> kingWhite = 1;
            }
            switch (piecesBlack.get(i).getType()) {
                case Pawn -> pawnsBlack++;
                case Rook -> rooksBlack++;
                case Knight -> knightsBlack++;
                case Bishop -> bishopsBlack++;
                case Queen -> queenBlack = 1;
                case King -> kingBlack = 1;
            }
        }

        String messageWhite = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesWhite.size()) +
                String.format("Pawns: %d<br>", pawnsWhite) +
                String.format("Rooks (Castles): %d<br>", rooksWhite) +
                String.format("Knights: %d<br>", knightsWhite) +
                String.format("Bishops: %d<br>", bishopsWhite) +
                String.format("Have Queen: %s<br>", queenWhite == 1 ? "true" : "false") +
                String.format("Have King: %s</html>", kingWhite == 1 ? "true" : "false");

        String messageBlack = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesBlack.size()) +
                String.format("Pawns: %d<br>", pawnsBlack) +
                String.format("Rooks (Castles): %d<br>", rooksBlack) +
                String.format("Knights: %d<br>", knightsBlack) +
                String.format("Bishops: %d<br>", bishopsBlack) +
                String.format("Have Queen: %s<br>", queenBlack == 1 ? "true" : "false") +
                String.format("Have King: %s</html>", kingBlack == 1 ? "true" : "false");

        playerLabel.setText(messageWhite);
        opponentLabel.setText(messageBlack);
    }

    private JLabel playerLabel;
    private JLabel opponentLabel;

    private void createInfo() {
        JPanel movesPanel = new JPanel();
        movesPanel.setName("moves_panel");
        Border movesBorder = BorderFactory.createLineBorder(Color.GRAY, 5);
        movesPanel.setBorder(movesBorder);
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));
        movesPanel.setBounds(820, 470, 260, 170);

        JPanel timerPanel = new JPanel();
        timerPanel.setName("timer_panel");
        Border timerBorder = BorderFactory.createLineBorder(Color.GRAY, 5);
        timerPanel.setBorder(timerBorder);
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBounds(820, 645, 260, 165);

        JPanel playerPanel = getPlayerInfoPanel(true),
               opponentPanel = getPlayerInfoPanel(false);
        playerPanel.setBounds(820, 10, 260, 225);
        playerPanel.setName("player_panel");
        opponentPanel.setBounds(820, 240, 260, 225);
        opponentPanel.setName("opponent_panel");

        Board.onPieceMoved(this::updateInfoBoards)
        updateInfoBoards();

        System.out.println(playerLabel.getText());
        System.out.println(opponentLabel.getText());

        add(playerPanel);
        add(opponentPanel);
        add(movesPanel);
        add(timerPanel);

        Component[] components = getComponents();
        util.Console.message(components[0].getSize().height);
    }
}