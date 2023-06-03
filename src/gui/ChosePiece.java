package gui;

import game.GameManager;
import util.Vector;

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

    private JButton getStylizedButton(int x, Image imageDir, ActionListener action) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBounds(x, 20, 50, 50);
        Image img = new ImageIcon(imageDir).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(img));
        btn.addActionListener(action);
        return btn;
    }

    private void createUserInterface() {
        JPanel panel = new JPanel(new FlowLayout());

        boolean isWhite = GameManager.instance.isWhite();

        JButton rookButton = getStylizedButton(10, gui.images.Image.IMAGES[isWhite ? 0 : 1][0].getImage(), e -> {
            System.out.println("rook clicked"); // TODO: dodaj zamenu za pawn kad dodje do kraja table
        });
        JButton knightButton = getStylizedButton(65, gui.images.Image.IMAGES[isWhite ? 0 : 1][1].getImage(), e -> {
            System.out.println("knight clicked"); // TODO: dodaj zamenu za pawn kad dodje do kraja table
        });
        JButton bishopButton = getStylizedButton(120, gui.images.Image.IMAGES[isWhite ? 0 : 1][2].getImage(), e -> {
            System.out.println("bishop clicked"); // TODO: dodaj zamenu za pawn kad dodje do kraja table
        });
        JButton queenButton = getStylizedButton(175, gui.images.Image.IMAGES[isWhite ? 0 : 1][3].getImage(), e -> {
            System.out.println("queen clicked"); // TODO: dodaj zamenu za pawn kad dodje do kraja table
        });

        panel.add(rookButton);
        panel.add(knightButton);
        panel.add(bishopButton);
        panel.add(queenButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}