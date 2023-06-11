package gui.images;

import game.Board;
import utility.customGui.CustomImage;
import utility.customGui.CustomLabel;
import utility.customGui.Colors;
import utility.math.Vector;
import utility.eventSystem.ArgumentEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Field class represents a customized image panel that represents a field on a chessboard.
 * It provides functionality to handle mouse events and display labels for the field position.
 */
@SuppressWarnings("unused")
public class Field extends CustomImage implements MouseListener {
    private final Vector boardPosition;
    private final boolean isGradient;

    /**
     * Constructs a Field object with the specified parameters.
     *
     * @param boardPosition the position of the field on the board
     * @param size          the dimensions of the field
     * @param isGradient    indicates whether the field has a gradient color
     */
    public Field(final Vector boardPosition, final Vector size, final boolean isGradient) {
        super();

        this.boardPosition = boardPosition;
        this.isGradient = isGradient;

        super.setDimensions(size.x, size.y);

        super.setColor(Color.blue);

        setLabels();

        setColor(isGradient ? Colors.FIELD.getLightColor() : Colors.FIELD.getDarkColor());

        addMouseListener(this);
    }

    /**
     * Retrieves the board position of the field.
     *
     * @return the board position as a Vector object
     */
    public final Vector getBoardPosition() {
        return this.boardPosition;
    }

    /**
     * Checks if the field has a gradient color.
     *
     * @return true if the field has a gradient color, false otherwise
     */
    public final boolean isGradient() {
        return this.isGradient;
    }

    private final String[] labels = {"a", "b", "c", "d", "e", "f", "g", "h"};

    /**
     * Sets the labels for the field based on its position.
     */
    private void setLabels() {
        setLayout(null);
        if (boardPosition.x == Board.LAST) {
            super.setLabel(labels[boardPosition.y]);
            imageLabel.setBounds(dimensions.width - 20, dimensions.height - 20, 15, 20);
        } else if (boardPosition.y == 0) {
            super.setLabel(Integer.toString(boardPosition.x + 1));
            imageLabel.setBounds(5, 5, 15, 15);
        }
        imageLabel.setForeground(Colors.FIELD.getColor(!isGradient));

        if (boardPosition.x == Board.LAST && boardPosition.y == 0) {
            JLabel lab = new CustomLabel(Integer.toString(boardPosition.x + 1));
            lab.setBounds(5, 5, 15, 15);
            lab.setForeground(!isGradient ? Colors.FIELD.getLightColor() : Colors.FIELD.getDarkColor());
            add(lab);
        }
    }

    /**
     * The onFieldClicked event is an ArgumentEvent that represents the event of a chessboard field being clicked.
     * It provides the board position of the clicked field as an argument.
     */
    public static ArgumentEvent<Vector> onFieldClicked = new ArgumentEvent<>();

    /**
     * Handles the mouseClicked event.
     * Invokes the onFieldClicked event with the board position as the argument.
     *
     * @param e the MouseEvent object
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        onFieldClicked.run(boardPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Not implemented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Not implemented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Not implemented
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Not implemented
    }
}