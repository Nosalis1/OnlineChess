package gui;

import game.Board;
import game.BoardData;
import game.GameManager;
import game.Piece;
import gui.images.Field;
import util.ColorGradient;
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

    gui.images.Field[][] fields = null;

    @SuppressWarnings("unused")
    public gui.images.Field[][] getFields() {
        return this.fields;
    }

    public gui.images.Field getField(final int x, final int y) {
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

    private String createInfoMessage(final String name,final int elo,
                                     final int piecesCount,final int piecesScore,
                                     final int pawnsCount,
                                     final int rooksCount,
                                     final int knightsCount,
                                     final int bishopsCount,
                                     final int queensCount,
                                     final int kingsCount) {
        return String.format("<html>Username: %s<br>", name)
                + String.format("ELO: %s<br>", elo)
                + String.format("CurrentPiecesCount: %d<br>", piecesCount)
                + String.format("CurrentPiecesScore: %d<br>", piecesScore)
                + String.format("Pawns: %d<br>", pawnsCount)
                + String.format("Rooks (Castles): %d<br>", rooksCount)
                + String.format("Knights: %d<br>", knightsCount)
                + String.format("Bishops: %d<br>", bishopsCount)
                + String.format("Have Queen: %s<br>", queensCount != 0 ? "true" : "false")
                + String.format("Have King: %s</html>", kingsCount != 0 ? "true" : "false");
    }

    private String createInfoMessage(final String name,final int elo,final int piecesCount,final int piecesScore,util.Array<Integer> data) {
        return createInfoMessage(name, elo,
                piecesCount, piecesScore,
                data.get(Piece.Type.Pawn.getCode()-1),
                data.get(Piece.Type.Rook.getCode()-1),
                data.get(Piece.Type.Knight.getCode()-1),
                data.get(Piece.Type.Bishop.getCode()-1),
                data.get(Piece.Type.Queen.getCode()-1),
                data.get(Piece.Type.King.getCode()-1)
        );
    }

    public void updateInfoTable(Piece piece) {
        final BoardData data = Board.instance.getData();

        String messageWhite = createInfoMessage("TODO:NAME",1,
                Board.instance.getWhitePieces().size(),
                data.getWhitePieceScore(),
                data.getWhiteData()
                );
        String messageBlack = createInfoMessage("TODO:OPPONENT NAME",-1,
                Board.instance.getBlackPieces().size(),
                data.getBlackPieceScore(),
                data.getBlackData()
        );

        playerLabel.setText(messageWhite);
        opponentLabel.setText(messageBlack);

        Color playColor = ColorGradient.FIELD.getColor(!GameManager.instance.canPlay());
        Color opponentColor = ColorGradient.FIELD.getColor(GameManager.instance.canPlay());
        playerPanel.setBorder(BorderFactory.createLineBorder(playColor, 5));
        opponentPanel.setBorder(BorderFactory.createLineBorder(opponentColor, 5));
    }

    public void updateMovesTable(Piece piece) {
        BoardData data = Board.instance.getData();
        util.Array<String> movesData = data.getMoves();

        if (movesTableModel.getRowCount() >= 15) movesTableModel.removeRow(0);
        String moveString = movesData.get(movesData.size() - 1);
        movesTableModel.addRow(new Object[]{moveString});
    }

    private JPanel playerPanel, opponentPanel;
    private JLabel playerLabel, opponentLabel;
    private DefaultTableModel movesTableModel;

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
        movesPanel.setBackground(ColorGradient.DARK.getColor(true));
        movesPanel.setForeground(Color.white);
        movesPanel.setTableHeader(null);
        movesPanel.setBounds(823, 475, 251, 240);
        JPanel movesPanelWrapper = new JPanel();
        movesPanelWrapper.add(movesPanel);
        movesPanelWrapper.setBackground(ColorGradient.DARK.getColor(false));
        movesPanelWrapper.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        movesPanelWrapper.setBounds(818, 470, 260, 250);
        add(movesPanelWrapper);

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
    // TODO: Dodaj metodu za odbrojavanje vremena
}