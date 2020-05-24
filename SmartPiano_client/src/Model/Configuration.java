package Model;

import Model.Utils.JsonUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

//A class that will storage all the user configuration. Yet to implement.
public class Configuration {
    private static final Map<Integer, String> keyBoardKeyConfiguration = new HashMap<>();
    private static final Map<String, Integer> keyCode_keyName = new HashMap<>();
    private static final KeyNotes keyNotes = JsonUtils.getKeyNotes("keyBoardConfig");


    private static String recKeyName;
    private static String goBackKeyName;

    public Configuration(){
        initializeKeyBoardKeyConfiguration(keyNotes);
        initializeKeyCode_keyName(keyNotes);
        recKeyName = "intro";
        goBackKeyName = "return";
    }

    public static String getRecKeyName() {
        return recKeyName;
    }

    public static String getGoBackKeyName() {
        return goBackKeyName;
    }

    public void setRecKeyName(KeyEvent keyEvent) {
        this.recKeyName = "";
    }

    /**
     * Initializes the keyboard-key configuration map.
     * @param keyNotes The initial configuration extracted from the JSON.
     */
    private void initializeKeyBoardKeyConfiguration(KeyNotes keyNotes) {

        for (KeyNote keyNote: keyNotes.getKeyNote()) {
            keyBoardKeyConfiguration.put(keyNote.getKeyBoard(), keyNote.getNote());
        }
    }

    /**
     * Initializes the keyboard-key configuration.
     * @param keyNotes The initial configuration extracted from the JSON.
     */
    private void initializeKeyCode_keyName(KeyNotes keyNotes){
        for (KeyNote keyNote: keyNotes.getKeyNote()) {
            keyCode_keyName.put(keyNote.getNote(), keyNote.getKeyBoard());
        }
    }

    /**
     * Updates the map when a change in the configuration is done.
     * @param keyToChange The key to change.
     * @param newKey The new key.
     * @return The status of the change.
     */
    public static int bindKey(String keyToChange, int newKey) {
        if(keyBoardKeyConfiguration.get(newKey) != null){
            return 1;
        }

        keyBoardKeyConfiguration.remove(keyCode_keyName.get(keyToChange));
        keyBoardKeyConfiguration.put(newKey, keyToChange);

        keyCode_keyName.remove(keyToChange);
        keyCode_keyName.put(keyToChange, newKey);

        return 0;
    }

    /**
     * When this view is closed ensure the configuration is saved in the JSON back again.
     */
    public static void saveKeyboardConfiguration () {

        JsonArray listNotes = new JsonArray();
        JsonObject container = new JsonObject();


        for (KeyNote keyNote: keyNotes.getKeyNote()) {
            JsonObject note = new JsonObject();
            note.addProperty("note", keyNote.getNote());
            note.addProperty("keyBoard", keyCode_keyName.get(keyNote.getNote()));
            listNotes.add(note);
        }

        container.add("keyNote", listNotes);

        //Write JSON file
        String f = new File("").getAbsolutePath();
        try (FileWriter file = new FileWriter(f.concat  ("\\SmartPiano_client\\src\\keyBoardConfig.json"))) {
            file.write(container.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getKeyBinding(int keyCodeFromKeyEvent) {
        return keyBoardKeyConfiguration.get(keyCodeFromKeyEvent);
    }

    public static int getKeyNameFromKeyEventCode(String name){
        return keyCode_keyName.get(name);
    }

}
