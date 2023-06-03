package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChosePiece extends Window implements ActionListener {
    public ChosePiece() {
        super("Chose piece");
        createUserInterface();
    }

    private JPanel getStylizedPanel(Image pieceImage, Rectangle bounds) {
        JPanel panel = new JPanel();
        panel.setBounds(bounds);
//        panel.set
        return panel;
    }

    private void createUserInterface() {
        JPanel panel = new JPanel();

        JPanel[] fields = {

        };

        for (JPanel field : fields)
            panel.add(field);

        add(panel);
//        JButton rookBtn = new JButton(new ImageIcon(Image.IMAGES[0][0].getImage()));
//        rookBtn.setBounds(10, 10, 50, 50);
//        JButton knightBtn = new JButton(new ImageIcon(Image.IMAGES[0][1].getImage()));
//        knightBtn.setBounds(65, 10, 50, 50);
//        JButton bishopBtn = new JButton(new ImageIcon(Image.IMAGES[0][2].getImage()));
//        bishopBtn.setBounds(110, 10, 50, 50);
//        JButton queenBtn = new JButton(new ImageIcon(Image.IMAGES[0][3].getImage()));
//        queenBtn.setBounds(165, 10, 50, 50);
//
//        add(rookBtn);
//        add(knightBtn);
//        add(bishopBtn);
//        add(queenBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}