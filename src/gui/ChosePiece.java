package gui;

import game.Piece;
import utility.math.Array;
import utility.customGui.Colors;
import utility.math.Vector;
import utility.eventSystem.ArgumentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChosePiece extends Window implements ActionListener {
    public ChosePiece() {
        super("Chose piece");
        this.setDimensions(new Vector(250, 80));
        setUndecorated(true);
        createUserInterface();
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
    }

    private final Array<JButton> buttons = new Array<>();

    private JButton getStylizedButton(int x, Image imageDir, ActionListener action) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBounds(7 + x, 15, 50, 50);
        btn.setBackground(Colors.DARK.getDarkColor());
        Image img = new ImageIcon(imageDir).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(img));
        btn.addActionListener(action);
        buttons.add(btn);
        return btn;
    }

    public static ArgumentEvent<Piece.Type> onTypeSelected = new ArgumentEvent<>();

    private void createUserInterface() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(Colors.DARK.getLightColor());
        panel.setLayout(null);

        JButton rookButton = getStylizedButton(10, GuiManager.pieceImages[0][0].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Rook);
            this.hideWindow();
        });
        JButton knightButton = getStylizedButton(65, GuiManager.pieceImages[0][1].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Knight);
            this.hideWindow();
        });
        JButton bishopButton = getStylizedButton(120, GuiManager.pieceImages[0][2].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Bishop);
            this.hideWindow();
        });
        JButton queenButton = getStylizedButton(175, GuiManager.pieceImages[0][3].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Queen);
            this.hideWindow();
        });

        panel.add(rookButton);
        panel.add(knightButton);
        panel.add(bishopButton);
        panel.add(queenButton);

        add(panel);
    }

    @Override
    public void showWindow() {
        Point position = GuiManager.getGameWindow().getLocation();
        Dimension dimension = GuiManager.getGameWindow().getSize();
        this.setPosition(new Vector(position.x + dimension.width / 2, position.y + dimension.height / 2));
        super.showWindow();
    }

    @Override
    public void hideWindow(){
        GuiManager.getGameWindow().setEnabled(true);
        super.hideWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}