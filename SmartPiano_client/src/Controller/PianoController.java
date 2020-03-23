package Controller;

import Model.Configuration;
import Model.Note;
import View.Piano;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

//The controller for the Piano view.
public class PianoController implements ActionListener, KeyListener {
    private Piano v;
    //A map with the keys that are concurrently sustaining.
    private Map<String, KeyPressed> sustainingKeys;
    private ActionListener[] actionListeners;

    /**
     * An empty constructor.
     */
    public PianoController(){
        sustainingKeys = new HashMap<>();
    }

    public ActionListener getActionListener(int i) {
        return actionListeners[i];
    }

    public void setView(Piano v) {
        this.v = v;
    }

    /**
     * Detects if a key was pressed with the mouse.
     * @param actionEvent The command assigned.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String command = actionEvent.getActionCommand();
        //Play the corresponding sound.
        new KeyPressed(v, command).start();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * Detects if a key (from the keyboard) was pressed.
     * @param keyEvent The information about the key pressed.
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        //Obtain the keyCode (w/0) from the keybinding configuration.
        String keyCode = Configuration.getKeyBinding(String.valueOf(keyEvent.getKeyChar()));
        //If it wasn't sustaining...
        if(sustainingKeys.get(keyCode) == null){
            //Add the note to the current sustaining notes.
            sustainingKeys.put(keyCode, new KeyPressed(v, keyCode));
            sustainingKeys.get(keyCode).start();
            v.pressButton(keyCode);
        }
    }

    /**
     * Detects if a key (from the keyboard) was released.
     * @param keyEvent The information about the key released.
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //Obtain the keyCode (w/0) from the keybinding configuration.
        String keyCode = Configuration.getKeyBinding(String.valueOf(keyEvent.getKeyChar()));
        //Cut off the sustaining.
        sustainingKeys.get(keyCode).setSustaining(false);
        //Remove it from the current sustaining notes.
        sustainingKeys.remove(keyCode);
        v.releaseButton(keyCode);
    }

    public void drop(Note note) {
        //v.drop(note);
    }
}
