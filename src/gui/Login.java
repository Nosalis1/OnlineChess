package gui;

import gui.design.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends Window implements ActionListener {
    public Login() {
        super("Login");
        createUserInterface();
    }

    JTextField usernameField;
    JPasswordField passwordField;

    private void createUserInterface() {

        setLayout(null);

        final int SPACING = 30;

        int x = 130;
        int y = 70;

        add(new CustomTitle("LOGIN", x, y));

        y += SPACING;

        add(new CustomLabel("Username:", x, y));

        y += SPACING - 10;

        add(usernameField = new CustomInputTextField(x, y));

        y += SPACING;

        add(new CustomLabel("Password:", x, y));

        y += SPACING - 10;

        add(passwordField = new CustomPasswordField(x, y));

        y += SPACING;

        add(new CustomButton("Login", x, y, this));

        y += SPACING;

        add(new CustomButton("Register", x, y, e -> {
            GuiManager.onButtonClick.run();
            this.hideWindow();
            GuiManager.getRegisterWindow().showWindow();
        }));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GuiManager.onButtonClick.run();

        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (game.users.User.login(userName, password)) {
            usernameField.setText("");
            passwordField.setText("");

            util.Console.message("Valid User", this);

            GuiManager.loggedIn();
        } else {
            usernameField.setText("");
            passwordField.setText("");

            JOptionPane.showMessageDialog(null, "Invalid Username/Password! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);

            util.Console.warning("Invalid User!", this);
        }
    }
}