package gui;

import util.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends Window implements ActionListener {
    public Login() {
        super("Login");
        createUserInterface();
    }

    TextField usernameField;
    JPasswordField passwordField;

    private void createUserInterface() {

        setLayout(null);

        final int SPACING = 30;

        int x = 130;
        int y = 70;

        JLabel label = new JLabel("LOGIN");
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

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(x, y, 120, 20);
        loginButton.addActionListener(this);

        add(loginButton);
        y += SPACING;

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(x, y, 120, 20);
        registerButton.addActionListener(e -> {
            this.hideWindow();
            GuiManager.instance.getRegisterWindow().showWindow();
        });

        add(registerButton);
        y += SPACING;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userName = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (game.users.User.login(userName, password)) {
            util.Console.message("Valid User", Console.PrintType.Gui);

            usernameField.setText("");
            passwordField.setText("");

            GuiManager.instance.loggedIn();
        } else {
            usernameField.setText("");
            passwordField.setText("");

            JOptionPane.showMessageDialog(null, "Invalid Username/Password! Try again.", "User Validation", JOptionPane.ERROR_MESSAGE);

            util.Console.warning("Invalid User!", Console.PrintType.Gui);
        }
    }
}