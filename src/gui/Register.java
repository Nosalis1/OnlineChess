package gui;

import gui.design.Button;
import gui.design.Label;
import gui.design.Title;

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

        add(new Title("REGISTER", x, y));

        y += SPACING;

        add(new Label("Username:", x, y));

        y += SPACING - 10;

        usernameField = new TextField();

        usernameField.setBounds(x, y, 120, 20);

        add(usernameField);
        y += SPACING;

        add(new Label("Password:", x, y));

        y += SPACING - 10;

        passwordField = new JPasswordField();

        passwordField.setBounds(x, y, 120, 20);

        add(passwordField);
        y += SPACING;

        add(new Button("Register", x, y, this));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiManager.onButtonClick.run();

        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (game.users.User.addUser(userName, password)) {
                util.Console.message("User registered!", this);

                usernameField.setText("");
                passwordField.setText("");

                GuiManager.registered();
            } else {
                usernameField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(null, "Username taken! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);

                util.Console.warning("User already exist!", this);
            }
        } catch (IOException ignored) {
        }
    }
}