package gui;

import game.users.User;
import socket.NetworkManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends Window implements ActionListener {
    public Menu() {
        super("Menu");
        createUserInterface();
    }

    JButton button = null;
    JLabel info = null;

    private void createUserInterface() {
        setLayout(null);

        final int SPACING = 30;

        int x = 130;
        int y = 100;

        JLabel label = new JLabel("MENU");
        label.setBounds(x, y, 120, 20);
        label.setForeground(Color.WHITE);

        add(label);
        y += SPACING;

        button = new JButton("Play");
        button.setBounds(x, y, 120, 20);
        button.addActionListener(this);

        add(button);
        y += SPACING;

        JButton removeUserButton = new JButton("Delete account");
        removeUserButton.setBounds(x, y, 120, 20);
        removeUserButton.addActionListener(e -> {
            GuiManager.onButtonClick.run();
            try {
                User.currentUser.removeUser();
                User.currentUser = null;
                JOptionPane.showMessageDialog(null, "Your account has successfully been deleted!", "User Removed", JOptionPane.INFORMATION_MESSAGE);
                GuiManager.instance.accountDeleted();
            } catch (IOException ex) {
                util.Console.error(String.format("User %s not removed", User.currentUser.getUserName()));
            }
        });

        add(removeUserButton);

        info = new JLabel("Waiting for opponent...");
        info.setBounds(x, y, 120, 20);
        info.setForeground(Color.WHITE);

        add(info);
        info.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiManager.onButtonClick.run();
        NetworkManager.connectClient();
        button.setVisible(false);
        info.setVisible(true);
    }
}
