package gui;

import game.Board;
import game.GameManager;
import game.Move;
import game.Piece;
import gui.images.Field;
import util.Array;
import util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Game extends Window {
    public Game() {
        super("Game");
        super.setSize(1100, 860);
        super.setLayout(null);
        super.setLocationRelativeTo(null);

        createTable();
        createInfo();
    }

        //comment
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

    public void clearFields() {
        for (gui.images.Field[] fieldRow : fields)
            for (gui.images.Field field : fieldRow)
                field.setImage(null);
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

    private JPanel getPlayerInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ColorGradient.DARK.getColor(false));

        JLabel label = new JLabel("Player info", SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        panel.setPreferredSize(new Dimension(260, label.getPreferredSize().height));
        panel.setFont(label.getFont().deriveFont(Font.BOLD, 20F));
        panel.add(label);
        panel.add(new JLabel());
        return panel;
    }

    public void updateInfoTable(Piece piece) {
        int pawnsWhite = 0, pawnsBlack = 0,
            rooksWhite = 0, rooksBlack = 0,
            knightsWhite = 0, knightsBlack = 0,
            bishopsWhite = 0, bishopsBlack = 0;
        boolean queenWhite = false, queenBlack = false,
            kingWhite = false, kingBlack = false;

        util.Array<Piece> piecesWhite = Board.instance.getWhitePieces(),
                          piecesBlack = Board.instance.getBlackPieces();
        for (int i=0; i<Board.instance.getWhitePieces().size(); i++) {
            switch (piecesWhite.get(i).getType()) {
                case Pawn -> pawnsWhite++;
                case Rook -> rooksWhite++;
                case Knight -> knightsWhite++;
                case Bishop -> bishopsWhite++;
                case Queen -> queenWhite = true;
                case King -> kingWhite = true;
            }
        }
        for (int i=0; i<Board.instance.getBlackPieces().size(); i++) {
            switch (piecesBlack.get(i).getType()) {
                case Pawn -> pawnsBlack++;
                case Rook -> rooksBlack++;
                case Knight -> knightsBlack++;
                case Bishop -> bishopsBlack++;
                case Queen -> queenBlack = true;
                case King -> kingBlack = true;
            }
        }

        int whiteScore=GameManager.instance.getWhiteScore(), blackScore=GameManager.instance.getBlackScore();

        String messageWhite = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesWhite.size()) +
                String.format("CurrentPiecesScore: %d<br>",GameManager.instance.isWhite()?whiteScore:blackScore) +
                String.format("Pawns: %d<br>", pawnsWhite) +
                String.format("Rooks (Castles): %d<br>", rooksWhite) +
                String.format("Knights: %d<br>", knightsWhite) +
                String.format("Bishops: %d<br>", bishopsWhite) +
                String.format("Have Queen: %s<br>", queenWhite ? "true" : "false") +
                String.format("Have King: %s</html>", kingWhite ? "true" : "false");

        String messageBlack = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesBlack.size()) +
                String.format("CurrentPiecesScore: %d<br>",GameManager.instance.isWhite()?whiteScore:blackScore) +
                String.format("Pawns: %d<br>", pawnsBlack) +
                String.format("Rooks (Castles): %d<br>", rooksBlack) +
                String.format("Knights: %d<br>", knightsBlack) +
                String.format("Bishops: %d<br>", bishopsBlack) +
                String.format("Have Queen: %s<br>", queenBlack ? "true" : "false") +
                String.format("Have King: %s</html>", kingBlack ? "true" : "false");

        playerLabel.setText(messageWhite);
        opponentLabel.setText(messageBlack);

        Color playColor = ColorGradient.FIELD.getColor(!GameManager.instance.canPlay());
        Color opponentColor = ColorGradient.FIELD.getColor(GameManager.instance.canPlay());
        playerPanel.setBorder(BorderFactory.createLineBorder(playColor, 5));
        opponentPanel.setBorder(BorderFactory.createLineBorder(opponentColor, 5));
    }

    public void updateMovesTable(Piece piece) {
        if (movesTableModel.getRowCount() <= 10) {
            String moveString = Board.instance.moves.get(Board.instance.moves.size() - 1);
            movesTableModel.addRow(new Object[] { moveString });
        }
        else {

        }
    }

    private JPanel playerPanel, opponentPanel;
    private JLabel playerLabel, opponentLabel;
    private DefaultTableModel movesTableModel;

    private void createInfo() {
        movesTableModel = new DefaultTableModel(new Object[] { "Column 1" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable movesPanel = new JTable(movesTableModel);
        movesPanel.setName("moves_panel");
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));
        movesPanel.setTableHeader(null);
        movesPanel.setBounds(823, 475, 251, 240);
        JPanel panel = new JPanel();
        panel.add(movesPanel);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        panel.setBounds(818, 470, 260, 250);
        add(panel);

        Board.instance.onPieceMoved.add(this::updateMovesTable);

        JPanel timerPanel = new JPanel();
        timerPanel.setName("timer_panel");
        Border timerBorder = BorderFactory.createLineBorder(Color.GRAY, 5);
        timerPanel.setBorder(timerBorder);
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBounds(818, 725, 260, 85);

        playerPanel = getPlayerInfoPanel();
        opponentPanel = getPlayerInfoPanel();

        playerPanel.setBounds(818, 10, 260, 225);
        playerPanel.setName("player_panel");
        opponentPanel.setBounds(818, 240, 260, 225);
        opponentPanel.setName("opponent_panel");

        playerLabel = (JLabel) playerPanel.getComponents()[1];
        playerLabel.setForeground(Color.white);
        opponentLabel = (JLabel) opponentPanel.getComponents()[1];
        opponentLabel.setForeground(Color.white);

        updateInfoTable(null);
        Board.instance.onPieceMoved.add(this::updateInfoTable);

        add(playerPanel);
        add(opponentPanel);
        add(movesPanel);
        add(timerPanel);
    }
}