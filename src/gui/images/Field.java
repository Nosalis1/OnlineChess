package gui.images;

import game.Board;
import gui.ColorGradient;
import util.Vector;
import util.events.ArgEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Field extends ImagePanel implements MouseListener {
    private final Vector boardPosition;

    public final Vector getBoardPosition() {
        return this.boardPosition;
    }

    private final boolean isGradient;

    public final boolean isGradient() {
        return this.isGradient;
    }

    public Field(final Vector boardPosition, final Vector size, final boolean isGradient) {
        super();

        this.boardPosition = boardPosition;
        this.isGradient = isGradient;

        super.setVecDimensions(size.X, size.Y);

        super.setColor(Color.blue);

        setLabels();

        setColor(ColorGradient.FIELD.getColor(this.isGradient));

        addMouseListener(this);
    }

    private final String[] labels = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private void setLabels() {
        if (boardPosition.X == Board.LAST) {
            super.setLabel(labels[boardPosition.Y]);
            super.setjLabelDimension(15, 15);
            super.setjLabelPosition(getVecDimensions().X - 20, getVecDimensions().Y - 20);
            super.getjLabel().setForeground(ColorGradient.FIELD.getColor(!isGradient));
        } else if (boardPosition.Y == 0) {
            super.setLabel(Integer.toString(boardPosition.X + 1));
            super.getjLabel().setForeground(ColorGradient.FIELD.getColor(!isGradient));
        }


        if (boardPosition.X == Board.LAST && boardPosition.Y == 0) {
            JLabel lab = new JLabel(Integer.toString(boardPosition.X + 1));
            lab.setBounds(10, 10, 10, 10);
            lab.setForeground(ColorGradient.FIELD.getColor(!isGradient));
            add(lab);
        }
    }

    public static util.events.ArgEvent<Vector> onFieldClicked = new ArgEvent<>();

    @Override
    public void mouseClicked(MouseEvent e) {
        onFieldClicked.run(boardPosition);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}