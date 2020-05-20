package Model.Utils;

import Model.KeyNotes;
import Model.ServerConnectionConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtils {
    public static ServerConnectionConfiguration getConnectionConfiguration(String fileName){
        String f = new File("").getAbsolutePath();

        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("\\SmartPiano_client\\src\\" + fileName + ".json")));
            Gson gson = new Gson();
            ServerConnectionConfiguration scc = gson.fromJson(file, ServerConnectionConfiguration.class);
            return scc;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f.concat("\\SmartPiano_client\\src\\" + fileName + ".json") + "\". Unable to connect to the server due to lack of connection params. Exiting.", "red");
            System.exit(0);
        }
        return null;
    }

    public static KeyNotes getKeyNotes (String fileName) {
        String f = new File("").getAbsolutePath();

        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("\\SmartPiano_client\\src\\" + fileName + ".json")));
            Gson gson = new Gson();
            KeyNotes keyNotes = gson.fromJson(file, KeyNotes.class);
            return keyNotes;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f.concat("\\SmartPiano_client\\src\\" + fileName + ".json") + "\". Unable to connect to the server due to lack of connection params. Exiting.", "red");
            System.exit(0);
        }
        return null;

    }



}
