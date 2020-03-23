package Model;

//A class containind some utils for the piano.
public class PianoManager {

    /**
     * Gets the name of the key given the code.
     * @param key keyCode of the desired key.
     * @return The name of the key.
     */
    public static String getKeyName(String key) {
        String k = "";
        try{
            switch (key){
                case "w/0":
                    k = "C2";
                    break;
                case "b/0":
                    k = "Db2";
                    break;
                case "w/1":
                    k = "D2";
                    break;
                case "b/1":
                    k = "Eb2";
                    break;
                case "w/2":
                    k = "E2";
                    break;
                case "w/3":
                    k = "F2";
                    break;
                case "b/3":
                    k = "Gb2";
                    break;
                case "w/4":
                    k = "G2";
                    break;
                case "b/4":
                    k = "Ab2";
                    break;
                case "w/5":
                    k = "A2";
                    break;
                case "b/5":
                    k = "Bb2";
                    break;
                case "w/6":
                    k = "B2";
                    break;
                case "w/7":
                    k = "C3";
                    break;
                case "b/7":
                    k = "Db3";
                    break;
                case "w/8":
                    k = "D3";
                    break;
                case "b/8":
                    k = "Eb3";
                    break;
                case "w/9":
                    k = "E3";
                    break;
                case "w/10":
                    k = "F3";
                    break;
                case "b/10":
                    k = "Gb3";
                    break;
                case "w/11":
                    k = "G3";
                    break;
                case "b/11":
                    k = "Ab3";
                    break;
                case "w/12":
                    k = "A3";
                    break;
                case "b/12":
                    k = "Bb3";
                    break;
                case "w/13":
                    k = "B3";
                    break;
                case "w/14":
                    k = "C4";
                    break;
                case "b/14":
                    k = "Db4";
                    break;
                case "w/15":
                    k = "D4";
                    break;
                case "b/15":
                    k = "Eb4";
                    break;
                case "w/16":
                    k = "E4";
                    break;
                case "w/17":
                    k = "F4";
                    break;
                case "b/17":
                    k = "Gb4";
                    break;
                case "w/18":
                    k = "G4";
                    break;
                case "b/18":
                    k = "Ab4";
                    break;
                case "w/19":
                    k = "A4";
                    break;
                case "b/19":
                    k = "Bb4";
                    break;
                case "w/20":
                    k = "B4";
                    break;
            }
            return k;
        } catch (NullPointerException ignore){}
        return null;
    }

    /**
     * Gets the code of the key given the name.
     * @param key Name of the key.
     * @return The code.
     */
    public static String getKeyCode(String key){
        String k = "";
        try{
            switch (key){
                case "C2":
                    k = "w/0";
                    break;
                case "Db2":
                    k = "b/0";
                    break;
                case "D2":
                    k = "w/1";
                    break;
                case "Eb2":
                    k = "b/1";
                    break;
                case "E2":
                    k = "w/2";
                    break;
                case "F2":
                    k = "w/3";
                    break;
                case "Gb2":
                    k = "b/3";
                    break;
                case "G2":
                    k = "w/4";
                    break;
                case "Ab2":
                    k = "b/4";
                    break;
                case "A2":
                    k = "w/5";
                    break;
                case "Bb2":
                    k = "b/5";
                    break;
                case "B2":
                    k = "w/6";
                    break;
                case "C3":
                    k = "w/7";
                    break;
                case "Db3":
                    k = "b/7";
                    break;
                case "D3":
                    k = "w/8";
                    break;
                case "Eb3":
                    k = "b/8";
                    break;
                case "E3":
                    k = "w/9";
                    break;
                case "F3":
                    k = "w/10";
                    break;
                case "Gb3":
                    k = "b/10";
                    break;
                case "G3":
                    k = "w/11";
                    break;
                case "Ab3":
                    k = "b/11";
                    break;
                case "A3":
                    k = "w/12";
                    break;
                case "Bb3":
                    k = "b/12";
                    break;
                case "B3":
                    k = "w/13";
                    break;
                case "C4":
                    k = "w/14";
                    break;
                case "Db4":
                    k = "b/14";
                    break;
                case "D4":
                    k = "w/15";
                    break;
                case "Eb4":
                    k = "b/15";
                    break;
                case "E4":
                    k = "w/16";
                    break;
                case "F4":
                    k = "w/17";
                    break;
                case "Gb4":
                    k = "b/17";
                    break;
                case "G4":
                    k = "w/18";
                    break;
                case "Ab4":
                    k = "b/18";
                    break;
                case "A4":
                    k = "w/19";
                    break;
                case "Bb4":
                    k = "b/19";
                    break;
                case "B4":
                    k = "w/20";
                    break;
            }
            return k;
        } catch (NullPointerException ignore){}
        return null;
    }
}
