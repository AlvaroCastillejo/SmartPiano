package Controller;

import Model.Configuration;
import View.Piano;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class PianoController implements ActionListener, KeyListener {
    private Piano v;
    private Map<String, KeyPressed> sustainingKeys;

    private ActionListener[] actionListeners;

    public PianoController(){
        sustainingKeys = new HashMap<>();
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

        new KeyPressed(v, command).start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        String keyCode = Configuration.getKeyBinding(String.valueOf(keyEvent.getKeyChar()));
        if(sustainingKeys.get(keyCode) == null){
            sustainingKeys.put(keyCode, new KeyPressed(v, keyCode));
            sustainingKeys.get(keyCode).start();
            v.pressButton(keyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        String keyCode = Configuration.getKeyBinding(String.valueOf(keyEvent.getKeyChar()));
        sustainingKeys.get(keyCode).setSustaining(false);
        sustainingKeys.remove(keyCode);
        v.releaseButton(keyCode);
    }
}
