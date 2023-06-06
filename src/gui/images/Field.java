package gui.images;

import game.Board;
import util.ColorGradient;
import util.Vector;
import util.events.ArgumentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Field class represents a chessboard field with a specific position and appearance.
 * <p>
 * It extends the ImagePanel class and implements the MouseListener interface.
 */
public class Field extends ImagePanel implements MouseListener {
    private final Vector boardPosition;

    /**
     * Gets the position of the field on the chessboard.
     *
     * @return the board position vector
     */
    @SuppressWarnings("unused")
    public final Vector getBoardPosition() {
        return this.boardPosition;
    }

    private final boolean isGradient;

    /**
     * Checks if the field has a gradient appearance.
     *
     * @return true if the field has a gradient, false otherwise
     */
    public final boolean isGradient() {
        return this.isGradient;
    }

    /**
     * Creates a new instance of the Field class with the specified board position, size, and gradient flag.
     *
     * @param boardPosition the position of the field on the chessboard
     * @param size          the size of the field
     * @param isGradient    the flag indicating if the field has a gradient appearance
     */
    public Field(final Vector boardPosition, final Vector size, final boolean isGradient) {
        super();

        this.boardPosition = boardPosition;
        this.isGradient = isGradient;

        super.setVecDimensions(size.x, size.y);

        super.setColor(Color.blue);

        setLabels();

        setColor(isGradient?ColorGradient.FIELD.getLightColor():ColorGradient.FIELD.getDarkColor());

        addMouseListener(this);
    }

    private final String[] labels = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private void setLabels() {
        if (boardPosition.x == Board.LAST) {
            super.setLabel(labels[boardPosition.y]);
            super.setjLabelDimension(15, 15);
            super.setjLabelPosition(getVecDimensions().x - 20, getVecDimensions().y - 20);
            super.getjLabel().setForeground(!isGradient?ColorGradient.FIELD.getLightColor():ColorGradient.FIELD.getDarkColor());
        } else if (boardPosition.y == 0) {
            super.setLabel(Integer.toString(boardPosition.x + 1));
            super.getjLabel().setForeground(!isGradient?ColorGradient.FIELD.getLightColor():ColorGradient.FIELD.getDarkColor());
        }

        if (boardPosition.x == Board.LAST && boardPosition.y == 0) {
            JLabel lab = new JLabel(Integer.toString(boardPosition.x + 1));
            lab.setBounds(10, 10, 10, 10);
            lab.setForeground(!isGradient?ColorGradient.FIELD.getLightColor():ColorGradient.FIELD.getDarkColor());
            add(lab);
        }
    }

    /**
     * The onFieldClicked event is an ArgEvent that represents the event of a chessboard field being clicked.
     * It provides the board position of the clicked field as an argument.
     */
    public static ArgumentEvent<Vector> onFieldClicked = new ArgumentEvent<>();

    /**
     * Event handler for the mouseClicked event.
     * Invokes the onFieldClicked event with the board position as the argument.
     *
     * @param e the MouseEvent object
     */
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