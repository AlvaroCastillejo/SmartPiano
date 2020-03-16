package Controller;

import View.Piano;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PianoController implements ActionListener {
    private Piano v;

    private ActionListener[] actionListeners;

    public PianoController(){

    }

    public ActionListener getActionListener(int i) {
        return actionListeners[i];
    }

    public void setView(Piano v) {
        this.v = v;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        System.out.println(command);

        new keyPressed(v, command).start();
    }
}
