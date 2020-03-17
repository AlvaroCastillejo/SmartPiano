package Model;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static final Map<String, String> keyBoardKeyConfiguration = new HashMap<>();

    public Configuration(){
        keyBoardKeyConfiguration.put("q", "w/0");
        keyBoardKeyConfiguration.put("2", "b/0");
        keyBoardKeyConfiguration.put("w", "w/1");
        keyBoardKeyConfiguration.put("3", "b/1");
        keyBoardKeyConfiguration.put("e", "w/2");
        keyBoardKeyConfiguration.put("r", "w/3");
        keyBoardKeyConfiguration.put("5", "b/3");
        keyBoardKeyConfiguration.put("t", "w/4");
        keyBoardKeyConfiguration.put("6", "b/4");
        keyBoardKeyConfiguration.put("y", "w/5");
        keyBoardKeyConfiguration.put("7", "b/5");
        keyBoardKeyConfiguration.put("u", "w/6");
        keyBoardKeyConfiguration.put("i", "w/7");
        keyBoardKeyConfiguration.put("9", "b/7");
        keyBoardKeyConfiguration.put("o", "w/8");
        keyBoardKeyConfiguration.put("0", "b/8");
        keyBoardKeyConfiguration.put("p", "w/9");
        keyBoardKeyConfiguration.put("<", "w/10");
        keyBoardKeyConfiguration.put("a", "b/10");
        keyBoardKeyConfiguration.put("z", "w/11");
        keyBoardKeyConfiguration.put("s", "b/11");
        keyBoardKeyConfiguration.put("x", "w/12");
        keyBoardKeyConfiguration.put("d", "b/12");
        keyBoardKeyConfiguration.put("c", "w/13");
        keyBoardKeyConfiguration.put("v", "w/14");
        keyBoardKeyConfiguration.put("g", "b/14");
        keyBoardKeyConfiguration.put("b", "w/15");
        keyBoardKeyConfiguration.put("h", "b/15");
        keyBoardKeyConfiguration.put("n", "w/16");
        keyBoardKeyConfiguration.put("m", "w/17");
        keyBoardKeyConfiguration.put("k", "b/17");
        keyBoardKeyConfiguration.put(",", "w/18");
        keyBoardKeyConfiguration.put("l", "b/18");
        keyBoardKeyConfiguration.put(".", "w/19");
        keyBoardKeyConfiguration.put("ñ", "b/19");
        keyBoardKeyConfiguration.put("-", "w/20");
        //keyBoardKeyConfiguration.put();
    }

    public static String getKeyBinding(String keyChar) {
        return keyBoardKeyConfiguration.get(keyChar);
    }
}
