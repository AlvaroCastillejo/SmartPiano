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
        String toReturn = "";
        try{
            switch (key){
                case "C2":
                    toReturn = "w/0";
                    break;
                case "Db2":
                    toReturn = "b/0";
                    break;
                case "D2":
                    toReturn = "w/1";
                    break;
                case "Eb2":
                    toReturn = "b/1";
                    break;
                case "E2":
                    toReturn = "w/2";
                    break;
                case "F2":
                    toReturn = "w/3";
                    break;
                case "Gb2":
                    toReturn = "b/3";
                    break;
                case "G2":
                    toReturn = "w/4";
                    break;
                case "Ab2":
                    toReturn = "b/4";
                    break;
                case "A2":
                    toReturn = "w/5";
                    break;
                case "Bb2":
                    toReturn = "b/5";
                    break;
                case "B2":
                    toReturn = "w/6";
                    break;
                case "C3":
                    toReturn = "w/7";
                    break;
                case "Db3":
                    toReturn = "b/7";
                    break;
                case "D3":
                    toReturn = "w/8";
                    break;
                case "Eb3":
                    toReturn = "b/8";
                    break;
                case "E3":
                    toReturn = "w/9";
                    break;
                case "F3":
                    toReturn = "w/10";
                    break;
                case "Gb3":
                    toReturn = "b/10";
                    break;
                case "G3":
                    toReturn = "w/11";
                    break;
                case "Ab3":
                    toReturn = "b/11";
                    break;
                case "A3":
                    toReturn = "w/12";
                    break;
                case "Bb3":
                    toReturn = "b/12";
                    break;
                case "B3":
                    toReturn = "w/13";
                    break;
                case "C4":
                    toReturn = "w/14";
                    break;
                case "Db4":
                    toReturn = "b/14";
                    break;
                case "D4":
                    toReturn = "w/15";
                    break;
                case "Eb4":
                    toReturn = "b/15";
                    break;
                case "E4":
                    toReturn = "w/16";
                    break;
                case "F4":
                    toReturn = "w/17";
                    break;
                case "Gb4":
                    toReturn = "b/17";
                    break;
                case "G4":
                    toReturn = "w/18";
                    break;
                case "Ab4":
                    toReturn = "b/18";
                    break;
                case "A4":
                    toReturn = "w/19";
                    break;
                case "Bb4":
                    toReturn = "b/19";
                    break;
                case "B4":
                    toReturn = "w/20";
                    break;
            }
            return toReturn;
        } catch (NullPointerException ignore){}
        return null;
    }

    public int getHexaCode(String keyCode){
        int toReturn = -1;
        if(getKeyName(keyCode) == null){
            return toReturn;
        }
        switch (getKeyName(keyCode)){
            case "C2":
                toReturn = 0x30;
                break;
            case "Db2":
                toReturn = 0x31;
                break;
            case "D2":
                toReturn = 0x32;
                break;
            case "Eb2":
                toReturn = 0x33;
                break;
            case "E2":
                toReturn = 0x34;
                break;
            case "F2":
                toReturn = 0x35;
                break;
            case "Gb2":
                toReturn = 0x36;
                break;
            case "G2":
                toReturn = 0x37;
                break;
            case "Ab2":
                toReturn = 0x38;
                break;
            case "A2":
                toReturn = 0x39;
                break;
            case "Bb2":
                toReturn = 0x3A;
                break;
            case "B2":
                toReturn = 0x3B;
                break;
            case "C3":
                toReturn = 0x3C;
                break;
            case "Db3":
                toReturn = 0x3D;
                break;
            case "D3":
                toReturn = 0x3F;
                break;
            case "Eb3":
                toReturn = 0x40;
                break;
            case "E3":
                toReturn = 0x41;
                break;
            case "F3":
                toReturn = 0x42;
                break;
            case "Gb3":
                toReturn = 0x43;
                break;
            case "G3":
                toReturn = 0x44;
                break;
            case "Ab3":
                toReturn = 0x45;
                break;
            case "A3":
                toReturn = 0x46;
                break;
            case "Bb3":
                toReturn = 0x47;
                break;
            case "B3":
                toReturn = 0x48;
                break;
            case "C4":
                toReturn = 0x49;
                break;
            case "Db4":
                toReturn = 0x4A;
                break;
            case "D4":
                toReturn = 0x4B;
                break;
            case "Eb4":
                toReturn = 0x4C;
                break;
            case "E4":
                toReturn = 0x4D;
                break;
            case "F4":
                toReturn = 0x4E;
                break;
            case "Gb4":
                toReturn = 0x4F;
                break;
            case "G4":
                toReturn = 0x50;
                break;
            case "Ab4":
                toReturn = 0x51;
                break;
            case "A4":
                toReturn = 0x52;
                break;
            case "Bb4":
                toReturn = 0x53;
                break;
            case "B4":
                toReturn = 0x54;
                break;
        }
        return toReturn;
    }
}
