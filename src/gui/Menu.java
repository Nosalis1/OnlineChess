package gui;

import audio.AudioManager;
import game.users.User;
import utility.customGui.CustomButton;
import utility.customGui.CustomTitle;
import networking.LocalClient;
import networking.NetworkManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends Window implements ActionListener {
    public Menu() {
        super("Menu");
        createUserInterface();
    }

    JButton playButton,deleteButton,stopButton = null;
    JLabel info = null;

    private void createUserInterface() {
        setLayout(null);

        final int SPACING = 30;

        int x = 130;
        int y = 100;

        add(new CustomTitle("MENU", x, y));

        y += SPACING;

        add(playButton = new CustomButton("Play", x, y, this));

        add(info = new CustomTitle("Waiting for opponent...", x, y));
        info.setVisible(false);

        y += SPACING;

        add(deleteButton = new CustomButton("Delete account", x, y, e -> {
            GuiManager.onButtonClick.run();
            try {
                User.currentUser.removeUser();
                User.currentUser = null;
                JOptionPane.showMessageDialog(null, "Your account has successfully been deleted!", "User Removed", JOptionPane.INFORMATION_MESSAGE);
                GuiManager.accountDeleted();
            } catch (IOException ex) {
                utility.Console.error(String.format("User %s not removed", User.currentUser.getUserName()));
            }
        }));
        add(stopButton = new CustomButton("Cancel",x,y, e->{
            GuiManager.onButtonClick.run();
            LocalClient.disconnect();
            playButton.setVisible(true);
            deleteButton.setVisible(true);
            stopButton.setVisible(false);
            info.setVisible(false);
        }));
        stopButton.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiManager.onButtonClick.run();
        NetworkManager.connectClient();
        playButton.setVisible(false);
        deleteButton.setVisible(false);
        stopButton.setVisible(true);
        info.setVisible(true);
        AudioManager.play("gameQueue.wav");
    }
}