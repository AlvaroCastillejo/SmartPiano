package Controller;


import View.MainMenuView;
import View.Piano;
import com.sun.tools.javac.Main;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {
    private MainMenuView v;
    public MenuController(MainMenuView v){
        this.v=v;
    }

    public MenuController() {

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        String command = actionEvent.getActionCommand();
        System.out.printf("%s",command);
        switch (command){
            case "PlayPiano":
                v.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    PianoController c = new PianoController();
                    Piano m = new Piano(c);
                    c.setView(m);
                    m.setVisible(true);
                });


        }
    }

    private void setVisible(boolean b) {
    }



}
