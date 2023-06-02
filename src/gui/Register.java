package gui;

import game.users.User;
import util.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Register extends Window implements ActionListener {
    public Register() {
        super("Register");
        createUserInterface();
    }

    TextField usernameField;
    JPasswordField passwordField;

    private void createUserInterface() {
        setLayout(null);
        final int SPACING = 30;
        int x = 130;
        int y = 100;

        JLabel label = new JLabel("Register");

        label.setBounds(x, y, 120, 20);
        label.setForeground(Color.WHITE);

        add(label);
        y += SPACING;

        label = new JLabel("Username:");

        label.setBounds(x, y, 120, 20);
        label.setForeground(Color.WHITE);

        add(label);
        y += SPACING - 10;

        usernameField = new TextField();

        usernameField.setBounds(x, y, 120, 20);

        add(usernameField);
        y += SPACING;

        label = new JLabel("Password:");

        label.setBounds(x, y, 120, 20);
        label.setForeground(Color.WHITE);

        add(label);
        y += SPACING - 10;

        passwordField = new JPasswordField();

        passwordField.setBounds(x, y, 120, 20);

        add(passwordField);
        y += SPACING;

        JButton button = new JButton("Register");

        button.setBounds(x, y, 120, 20);
        button.addActionListener(this);

        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (User.addUser(userName, password)) {
                util.Console.message("User registered!", Console.PrintType.Gui);
                GuiManager.instance.registered();
            }
            else {
                usernameField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(null, "Username taken! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);

                util.Console.warning("User already exist!", Console.PrintType.Gui);
            }
        } catch (IOException ignored) {}

//        if (game.users.User.login(userName, password)) {
//            util.Console.message("Valid User", Console.PrintType.Gui);
//            GuiManager.instance.loggedIn();
//        } else {
//            usernameField.setText("");
//            passwordField.setText("");
//
//            JOptionPane.showMessageDialog(null, "Invalid Username/Password! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);
//
//            util.Console.warning("Invalid User!", Console.PrintType.Gui);
//        }
    }
}