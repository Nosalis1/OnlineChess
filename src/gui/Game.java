package gui;

import game.Board;
import game.Piece;
import gui.images.Field;
import util.Array;
import util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Game extends Window {
    public static final Game instance = new Game();

    public Game() {
        super("Game");
        super.setSize(1100, 860);
        super.setLayout(null);
        super.setLocationRelativeTo(null);

        createTable();
        createInfo();
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

    private JPanel getPlayerInfoPanel(boolean isWhite, Color borderColor) {
        util.Array<Piece> pieces = isWhite ? Board.getWhitePieces() : Board.getBlackPieces();
        int pawns = 0,
            rooks = 0,
            knights = 0,
            bishops = 0;
        boolean queen = false,
                king = false;

        for (int i=0; i<pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if (piece != null)
                switch (piece.getType()) {
                    case Pawn -> pawns++;
                    case Rook -> rooks++;
                    case Knight -> knights++;
                    case Bishop -> bishops++;
                    case Queen -> queen = true;
                    case King -> king = true;
                }
        }

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(borderColor, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelPlayer = new JLabel("Player info", SwingConstants.CENTER);
        panel.setPreferredSize(new Dimension(260, labelPlayer.getPreferredSize().height));
        panel.setFont(labelPlayer.getFont().deriveFont(Font.BOLD, 20F));
        panel.add(labelPlayer);
        panel.add(new JLabel(String.format("Username: %s", "ime")));
        panel.add(new JLabel(String.format("ELO: %s", "elo")));
        panel.add(new JLabel(String.format("CurrentPiecesCount: %d", pieces.size())));
        panel.add(new JLabel(String.format("Pawns: %d", pawns)));
        panel.add(new JLabel(String.format("Rooks (Castles): %d", rooks)));
        panel.add(new JLabel(String.format("Knights: %d", knights)));
        panel.add(new JLabel(String.format("Bishops: %d", bishops)));
        panel.add(new JLabel(String.format("Have Queen: %s", queen ? "true" : "false")));
        panel.add(new JLabel(String.format("Have King: %s", king ? "true" : "false")));
        return panel;
    }

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

        JPanel playerPanel = getPlayerInfoPanel(true, Color.LIGHT_GRAY),
               opponentPanel = getPlayerInfoPanel(false, Color.GRAY);
        playerPanel.setBounds(820, 10, 260, 225);
        playerPanel.setName("player_panel");
        opponentPanel.setBounds(820, 240, 260, 225);
        opponentPanel.setName("opponent_panel");

        util.Array<JPanel> panels = new Array<>();
        panels.replace(new JPanel[] {playerPanel, opponentPanel, movesPanel, timerPanel});

        util.Console.message(panels.get(0).getName());
        util.Console.message(panels.get(1).getName());
        util.Console.message(panels.get(2).getName());
        util.Console.message(panels.get(3).getName());

        add("playerPanel", playerPanel);
        add("opponentPanel", opponentPanel);
        add("movesPanel", movesPanel);
        add("timerPanel", timerPanel);

        Component[] components = getComponents();
        util.Console.message(components[0].getSize().height);
    }
}