package Model.Utils;

import Model.ServerConfiguration;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonServerUtils {
    public static ServerConfiguration getServerConfiguration(String fileName){
        String f = new File("").getAbsolutePath();

        try{
            JsonReader file = new JsonReader(new FileReader(f.concat("\\SmartPiano_server\\src\\" + fileName + ".json")));
            Gson gson = new Gson();
            ServerConfiguration sc = gson.fromJson(file, ServerConfiguration.class);
            return sc;
        } catch (FileNotFoundException e) {
            Output.print("Couldn't find \"" + f.concat("\\SmartPiano_server\\src\\" + fileName + ".json") + "\". Unable to start the server due to lack of connection params. Exiting.", "red");
            System.exit(0);
        }
        return null;
    }
}
