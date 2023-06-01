package gui;

import socket.LocalClient;
import socket.NetworkManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends Window implements ActionListener {
    public Menu(){
        super("Menu");
        createUserInterface();
    }

    private void createUserInterface(){
        setLayout(null);

        final int SPACING = 30;

        int x = 130;
        int y = 100;

        JLabel label = new JLabel("MENU");

        label.setBounds(x, y, 120, 20);
        label.setForeground(Color.WHITE);

        add(label);
        y += SPACING;

        JButton button = new JButton();

        button.setText("Play");
        button.setBounds(x, y, 120, 20);
        button.addActionListener(this);

        add(button);
        y += SPACING;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hideWindow();
        NetworkManager.connectClient();
        //TODO: DISABLE BUTTONS AND ENABLE "WAITING FOR OPPONENT..." LABEL
    }
}
