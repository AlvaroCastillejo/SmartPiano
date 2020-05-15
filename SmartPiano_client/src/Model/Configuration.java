package Model;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

//A class that will storage all the user configuration. Yet to implement.
public class Configuration {
    private static final Map<Integer, String> keyBoardKeyConfiguration = new HashMap<>();
    private static final Map<String, Integer> keyCode_keyName = new HashMap<>();

    private static String recKeyName;
    private static String goBackKeyName;

    public Configuration(KeyNotes keyNotes){
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

    private void initializeKeyBoardKeyConfiguration(KeyNotes keyNotes) {

        for (KeyNote keyNote: keyNotes.getKeyNote()) {
            keyBoardKeyConfiguration.put(keyNote.getKeyBoard(), keyNote.getNote());
        }

        /*
        keyBoardKeyConfiguration.put(0, "w/0");
        keyBoardKeyConfiguration.put(49, "b/0");
        keyBoardKeyConfiguration.put(81, "w/1");
        keyBoardKeyConfiguration.put(50, "b/1");
        keyBoardKeyConfiguration.put(87, "w/2");
        keyBoardKeyConfiguration.put(69, "w/3");
        keyBoardKeyConfiguration.put(52, "b/3");
        keyBoardKeyConfiguration.put(82, "w/4");
        keyBoardKeyConfiguration.put(53, "b/4");
        keyBoardKeyConfiguration.put(84, "w/5");
        keyBoardKeyConfiguration.put(54, "b/5");
        keyBoardKeyConfiguration.put(89, "w/6");
        keyBoardKeyConfiguration.put(16, "w/7");
        keyBoardKeyConfiguration.put(20, "b/7");
        keyBoardKeyConfiguration.put(153, "w/8");
        keyBoardKeyConfiguration.put(65, "b/8");
        keyBoardKeyConfiguration.put(90, "w/9");
        keyBoardKeyConfiguration.put(88, "w/10");
        keyBoardKeyConfiguration.put(68, "b/10");
        keyBoardKeyConfiguration.put(67, "w/11");
        keyBoardKeyConfiguration.put(70, "b/11");
        keyBoardKeyConfiguration.put(86, "w/12");
        keyBoardKeyConfiguration.put(71, "b/12");
        keyBoardKeyConfiguration.put(32, "w/13");
        keyBoardKeyConfiguration.put(66, "w/14");
        keyBoardKeyConfiguration.put(72, "b/14");
        keyBoardKeyConfiguration.put(78, "w/15");
        keyBoardKeyConfiguration.put(74, "b/15");
        keyBoardKeyConfiguration.put(77, "w/16");
        keyBoardKeyConfiguration.put(44, "w/17");
        keyBoardKeyConfiguration.put(76, "b/17");
        keyBoardKeyConfiguration.put(46, "w/18");
        keyBoardKeyConfiguration.put(80, "b/18");
        keyBoardKeyConfiguration.put(45, "w/19");
        keyBoardKeyConfiguration.put(129, "b/19");
        keyBoardKeyConfiguration.put(525, "w/20");
        keyBoardKeyConfiguration.put(10, "rec");
        keyBoardKeyConfiguration.put(8, "goBack");

         */
    }

    private void initializeKeyCode_keyName(KeyNotes keyNotes){
        for (KeyNote keyNote: keyNotes.getKeyNote()) {
            keyCode_keyName.put(keyNote.getNote(), keyNote.getKeyBoard());
        }

        /*
        keyCode_keyName.put("w/0", 0);
        keyCode_keyName.put("b/0", 49);
        keyCode_keyName.put("w/1", 81);
        keyCode_keyName.put("b/1", 50);
        keyCode_keyName.put("w/2", 87);
        keyCode_keyName.put("w/3", 69);
        keyCode_keyName.put("b/3", 52);
        keyCode_keyName.put("w/4", 82);
        keyCode_keyName.put("b/4", 53);
        keyCode_keyName.put("w/5", 84);
        keyCode_keyName.put("b/5", 54);
        keyCode_keyName.put("w/6", 89);
        keyCode_keyName.put("w/7", 16);
        keyCode_keyName.put("b/7", 20);
        keyCode_keyName.put("w/8", 153);
        keyCode_keyName.put("b/8", 65);
        keyCode_keyName.put("w/9", 90);
        keyCode_keyName.put("w/10",88);
        keyCode_keyName.put("b/10",68);
        keyCode_keyName.put("w/11",67);
        keyCode_keyName.put("b/11",70);
        keyCode_keyName.put("w/12",86);
        keyCode_keyName.put("b/12",71);
        keyCode_keyName.put("w/13",32);
        keyCode_keyName.put("w/14",66);
        keyCode_keyName.put("b/14",72);
        keyCode_keyName.put("w/15",78);
        keyCode_keyName.put("b/15",74);
        keyCode_keyName.put("w/16",77);
        keyCode_keyName.put("w/17",44);
        keyCode_keyName.put("b/17",76);
        keyCode_keyName.put("w/18",46);
        keyCode_keyName.put("b/18",80);
        keyCode_keyName.put("w/19",45);
        keyCode_keyName.put("b/19",129);
        keyCode_keyName.put("w/20",525);
        keyCode_keyName.put("rec", 13);

         */
    }


    public static int bindKey(String keyToChange, int newKey) {
        /*
        keyBoardKeyConfiguration -> 8 "back"
        keyBoardKeyConfiguration -> 10 "rec"

        keyCode_keyName -> "back" 8
        keyCode_keyName -> "rec" 10

        cambio el rec por el back
        */

        if(keyBoardKeyConfiguration.get(newKey) != null){
            return 1;
        }


        keyBoardKeyConfiguration.remove(keyCode_keyName.get(keyToChange));
        keyBoardKeyConfiguration.put(newKey, keyToChange);

        keyCode_keyName.remove(keyToChange);
        keyCode_keyName.put(keyToChange, newKey);

        return 0;
    }


    public static String getKeyBinding(int keyCodeFromKeyEvent) {
        return keyBoardKeyConfiguration.get(keyCodeFromKeyEvent);
    }

    public static int getKeyNameFromKeyEventCode(String name){
        return keyCode_keyName.get(name);
    }

}
