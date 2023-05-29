package gui;

import game.Board;
import gui.images.Field;
import util.Vector;

import javax.swing.*;
import java.awt.*;

public class Game extends Window {
    public static final Game instance = new Game();

    public Game() {
        super("Game");
        super.setSize(1100, 860);
        super.setLayout(null);
        super.setLocationRelativeTo(null);

        createTable();
        createInfo();
    }

    gui.images.Field[][] fields = null;

    public gui.images.Field[][] getFields() {
        return this.fields;
    }

    public gui.images.Field getField(final int x,final int y) {
        return this.fields[x][y];
    }

    public gui.images.Field getField(final Vector at) {
        return getField(at.X, at.Y);
    }

    private void createTable() {
        JPanel tablePanel = new JPanel();

        tablePanel.setLayout(new GridLayout(Board.SIZE, Board.SIZE));
        tablePanel.setBounds(10, 10, 800, 800);

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

    private void createInfo(){
        //TODO: OVDE KOLEGA LUKA ODRADITE TRAZENI DIZAJN
    }
}