package gui;

import game.*;
import utility.customGui.CustomLabel;
import gui.images.Field;
import utility.customGui.Colors;
import utility.math.Array;
import utility.math.Vector;

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

    gui.images.Field[][] fields = null;

    @SuppressWarnings("unused")
    public gui.images.Field[][] getFields() {
        return this.fields;
    }

    public gui.images.Field getField(final int x, final int y) {
        return this.fields[x][y];
    }

    public gui.images.Field getField(final Vector at) {
        return getField(at.x, at.y);
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
        panel.setBackground(Colors.DARK.getDarkColor());

        JLabel label = new CustomLabel("Player info");
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(260, label.getPreferredSize().height));

        panel.add(label);
        panel.add(new CustomLabel());
        return panel;
    }

    private String createInfoMessage(final String name, final int elo, final int piecesCount, final int piecesScore, final int pawnsCount, final int rooksCount, final int knightsCount, final int bishopsCount, final int queensCount, final int kingsCount) {
        return String.format("<html>Username: %s<br>", name) + String.format("ELO: %s<br>", elo) + String.format("CurrentPiecesCount: %d<br>", piecesCount) + String.format("CurrentPiecesScore: %d<br>", piecesScore) + String.format("Pawns: %d<br>", pawnsCount) + String.format("Rooks (Castles): %d<br>", rooksCount) + String.format("Knights: %d<br>", knightsCount) + String.format("Bishops: %d<br>", bishopsCount) + String.format("Have Queen: %s<br>", queensCount != 0 ? "true" : "false") + String.format("Have King: %s</html>", kingsCount != 0 ? "true" : "false");
    }

    private String createInfoMessage(final String name, final int elo, final int piecesCount, final int piecesScore, Array<Integer> data) {
        return createInfoMessage(name, elo, piecesCount, piecesScore, data.get(Piece.Type.Pawn.getCode() - 1), data.get(Piece.Type.Rook.getCode() - 1), data.get(Piece.Type.Knight.getCode() - 1), data.get(Piece.Type.Bishop.getCode() - 1), data.get(Piece.Type.Queen.getCode() - 1), data.get(Piece.Type.King.getCode() - 1));
    }

    public void updateInfoTable(Move ignore) {
        final BoardData data = Board.instance.getData();

        String messageWhite = createInfoMessage(GameManager.localUser.getUserName(), 1, data.white.getPieces().size(), data.white.getScore(), data.white.getPiecesCount());
        String messageBlack = createInfoMessage(GameManager.opponent.getUserName(), -1, data.black.getPieces().size(), data.black.getScore(), data.black.getPiecesCount());

        playerLabel.setText(messageWhite);
        opponentLabel.setText(messageBlack);

        Color playColor = Colors.FIELD.getColor(GameManager.localUser.canPlay());
        Color opponentColor = Colors.FIELD.getColor(!GameManager.localUser.canPlay());
        playerPanel.setBorder(BorderFactory.createLineBorder(playColor, 5));
        opponentPanel.setBorder(BorderFactory.createLineBorder(opponentColor, 5));
    }

    public void updateMovesTable(Move ignore) {
        BoardData data = Board.instance.getData();
        Array<String> movesData = data.getMoves();

        if (movesTableModel.getRowCount() >= 15) movesTableModel.removeRow(0);
        String moveString = movesData.get(movesData.size() - 1);
        movesTableModel.addRow(new Object[]{moveString});
    }

    private JPanel playerPanel, opponentPanel;
    private JLabel playerLabel, opponentLabel, playerTimeLabel, opponentTimeLabel;
    private DefaultTableModel movesTableModel;

    @SuppressWarnings("unused")
    public JLabel getPlayerTimeLabel() {
        return playerTimeLabel;
    }

    @SuppressWarnings("unused")
    public JLabel getOpponentTimeLabel() {
        return opponentTimeLabel;
    }

    private void createInfo() {
        movesTableModel = new DefaultTableModel(new Object[]{"Column 1"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable movesPanel = new JTable(movesTableModel);
        movesPanel.setName("moves_panel");
        movesPanel.setLayout(new BoxLayout(movesPanel, BoxLayout.Y_AXIS));
        movesPanel.setBackground(Colors.DARK.getLightColor());
        movesPanel.setForeground(Color.white);
        movesPanel.setTableHeader(null);
        movesPanel.setBounds(823, 475, 251, 240);
        JPanel movesPanelWrapper = new JPanel();
        movesPanelWrapper.add(movesPanel);
        movesPanelWrapper.setBackground(Colors.DARK.getDarkColor());
        movesPanelWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        movesPanelWrapper.setBounds(818, 470, 260, 250);
        add(movesPanelWrapper);

        Board.instance.onMoveDone.add(this::updateMovesTable);

        JPanel timerPanel = new JPanel();
        timerPanel.setName("timer_panel");
        Border timerBorder = BorderFactory.createLineBorder(Color.GRAY, 5);
        timerPanel.setBorder(timerBorder);
        timerPanel.setLayout(new BoxLayout(timerPanel, BoxLayout.Y_AXIS));
        timerPanel.setBounds(818, 725, 260, 85);
        timerPanel.setBackground(Colors.DARK.getLightColor());
        playerTimeLabel = new CustomLabel();
        opponentTimeLabel = new CustomLabel();
        playerTimeLabel.setBounds(10, 10, 120, 20);
        opponentTimeLabel.setBounds(10, 30, 120, 20);
        playerTimeLabel.setForeground(Color.WHITE);
        opponentTimeLabel.setForeground(Color.WHITE);
        playerTimeLabel.setText("5:00");
        opponentTimeLabel.setText("5:00");
        timerPanel.add(playerTimeLabel);
        timerPanel.add(opponentTimeLabel);

        // TODO: NAPRAVI TAJMER OBJEKAT I DODAJ "TIMER.SETTIMERSTATE(TRUE);" ZA STARTOVANJE TAJMERA) timer = new GameClock(playerTimeLabel, opponentTimeLabel);
        GameClock clock = new GameClock(playerTimeLabel,opponentTimeLabel);
        GameManager.onGameStarted.addAction(()-> clock.setTimerState(true));
        GameManager.onGameEnded.addAction(()-> clock.setTimerState(false));

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

        // updateInfoTable(null);//TODO:CHECK THIS LATER
        Board.instance.onMoveDone.add(this::updateInfoTable);

        add(playerPanel);
        add(opponentPanel);
        add(movesPanel);
        add(timerPanel);
    }
}