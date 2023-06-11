package gui;

import utility.customGui.CustomButton;
import utility.customGui.CustomLabel;
import utility.customGui.CustomTitle;

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

    /**
     * Creates the user interface for the register window.
     */
    private void createUserInterface() {
        setLayout(null);
        final int SPACING = 30;
        int x = 130;
        int y = 100;

        add(new CustomTitle("REGISTER", x, y));

        y += SPACING;

        add(new CustomLabel("Username:", x, y));

        y += SPACING - 10;

        usernameField = new TextField();

        usernameField.setBounds(x, y, 120, 20);

        add(usernameField);
        y += SPACING;

        add(new CustomLabel("Password:", x, y));

        y += SPACING - 10;

        passwordField = new JPasswordField();

        passwordField.setBounds(x, y, 120, 20);

        add(passwordField);
        y += SPACING;

        add(new CustomButton("Register", x, y, this));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiManager.onButtonClick.run();

        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (game.users.User.addUser(userName, password)) {
                utility.Console.message("User registered!", this);

                usernameField.setText("");
                passwordField.setText("");

                GuiManager.registered();
            } else {
                usernameField.setText("");
                passwordField.setText("");

                JOptionPane.showMessageDialog(null, "Username taken! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);

                utility.Console.warning("User already exist!", this);
            }
        } catch (IOException ignored) {
        }
    }
}