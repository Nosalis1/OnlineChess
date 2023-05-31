package gui;

import com.sun.tools.jconsole.JConsoleContext;
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

    private JPanel getPlayerInfoPanel(boolean isWhite) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(isWhite ? Color.LIGHT_GRAY : Color.GRAY, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Player info", SwingConstants.CENTER);
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

        String messageWhite = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesWhite.size()) +
                String.format("Pawns: %d<br>", pawnsWhite) +
                String.format("Rooks (Castles): %d<br>", rooksWhite) +
                String.format("Knights: %d<br>", knightsWhite) +
                String.format("Bishops: %d<br>", bishopsWhite) +
                String.format("Have Queen: %s<br>", queenWhite ? "true" : "false") +
                String.format("Have King: %s</html>", kingWhite ? "true" : "false");

        String messageBlack = String.format("<html>Username: %s<br>", "ime") +
                String.format("ELO: %s<br>", "elo") +
                String.format("CurrentPiecesCount: %d<br>", piecesBlack.size()) +
                String.format("Pawns: %d<br>", pawnsBlack) +
                String.format("Rooks (Castles): %d<br>", rooksBlack) +
                String.format("Knights: %d<br>", knightsBlack) +
                String.format("Bishops: %d<br>", bishopsBlack) +
                String.format("Have Queen: %s<br>", queenBlack ? "true" : "false") +
                String.format("Have King: %s</html>", kingBlack ? "true" : "false");

        playerLabel.setText(messageWhite);
        opponentLabel.setText(messageBlack);
    }

    public void updateMovesTable(Piece piece) {
        if (Board.instance.moves.size() > 0) {
            if (movesTableModel.getRowCount() <= 10) {
                String moveString = Board.instance.moves.get(Board.instance.moves.size() - 1);
                util.Console.message(moveString);
                movesTableModel.addRow(new Object[] { moveString });
            }
            else {

            }
        }
    }

    private JLabel playerLabel;
    private JLabel opponentLabel;
    private DefaultTableModel movesTableModel;

    private void createInfo() {
        movesTableModel = new DefaultTableModel(new Object[] { "Column 1" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable movesPanel = new JTable(movesTableModel);
        movesPanel.setName("moves_panel");
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(movesPanel);
        scrollPane.setBounds(820, 470, 260, 170);
        Border movesBorder = BorderFactory.createLineBorder(Color.GRAY, 5);
        scrollPane.setBorder(movesBorder);
        movesPanel.setTableHeader(null);
        add(scrollPane, BorderLayout.CENTER);

        Board.instance.onPieceMoved.add(this::updateMovesTable);

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

        playerLabel = (JLabel) playerPanel.getComponents()[1];
        opponentLabel = (JLabel) opponentPanel.getComponents()[1];

        updateInfoTable(null);
        Board.instance.onPieceMoved.add(this::updateInfoTable);

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