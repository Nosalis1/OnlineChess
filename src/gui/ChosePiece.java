package gui;

import game.Piece;
import util.Array;
import util.ColorGradient;
import util.Vector;
import util.events.ArgumentEvent;

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

    private final util.Array<JButton> buttons = new Array<>();

    private JButton getStylizedButton(int x, Image imageDir, ActionListener action) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBounds(7 + x, 15, 50, 50);
        btn.setBackground(ColorGradient.DARK.getDarkColor());
        Image img = new ImageIcon(imageDir).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(img));
        btn.addActionListener(action);
        buttons.add(btn);
        return btn;
    }

    public static ArgumentEvent<Piece.Type> onTypeSelected = new ArgumentEvent<>();

    private void createUserInterface() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(ColorGradient.DARK.getLightColor());
        panel.setLayout(null);

        boolean isWhite = true;//GameManager.localUser.isWhite();//TODO:LATER

        JButton rookButton = getStylizedButton(10, gui.images.Image.IMAGES[isWhite ? 0 : 1][0].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Rook);
            this.hideWindow();
        });
        JButton knightButton = getStylizedButton(65, gui.images.Image.IMAGES[isWhite ? 0 : 1][1].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Knight);
            this.hideWindow();
        });
        JButton bishopButton = getStylizedButton(120, gui.images.Image.IMAGES[isWhite ? 0 : 1][2].getImage(), e -> {
            onTypeSelected.run(Piece.Type.Bishop);
            this.hideWindow();
        });
        JButton queenButton = getStylizedButton(175, gui.images.Image.IMAGES[isWhite ? 0 : 1][3].getImage(), e -> {
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