package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println(actionEvent.getActionCommand());
    }
}
