package gui;

import game.Board;
import game.BoardData;
import game.GameManager;
import game.Piece;
import util.Array;
import util.Vector;
import util.events.ArgEvent;

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
    }

    private final util.Array<JButton> buttons = new Array<>();

    private JButton getStylizedButton(int x, Image imageDir, ActionListener action) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBounds(x, 20, 50, 50);
        Image img = new ImageIcon(imageDir).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(img));
        btn.addActionListener(action);
        buttons.add(btn);
        return btn;
    }

    public static util.events.ArgEvent<Piece.Type> onTypeSelected = new ArgEvent<>();

    private void createUserInterface() {
        JPanel panel = new JPanel(new FlowLayout());

        boolean isWhite = GameManager.localUser.isWhite();

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
        Point position = GuiManager.instance.getGameWindow().getLocation();
        this.setPosition(new Vector(position.x + 20, position.y + 20));
        super.showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}