package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends  Window implements ActionListener {

    public static final Menu instance = new Menu();

    public Menu(){
        super("Menu");
        createUserInterface();
    }

    private void createUserInterface(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
