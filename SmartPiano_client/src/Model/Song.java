package Model;

import Controller.PianoController;
import View.Piano;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Song extends Thread {
    Map<String, Note> currentNotes;
    LinkedList<String> instructionBuffer;
    Piano piano;
    PianoController pianoController;

    public Song(String fileName, PianoController c){
        currentNotes = new HashMap<>();
        instructionBuffer = new LinkedList<>();
        this.pianoController = c;

        String f = new File("").getAbsolutePath().concat("\\SmartPiano_client\\src\\Model\\Assets\\Music\\" + fileName);

        String line = null;

        try{
            FileReader fileReader = new FileReader(f);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            for(int i = 0; i < 6; i++){
                //Ignore format information. Metadata will be added here.
                line = bufferedReader.readLine();
            }
            String songName = bufferedReader.readLine().substring(1);
            System.out.println("Reproducing:" + songName);

            for(int i = 0; !line.equals("\\"); i++){
                try{
                    line = bufferedReader.readLine();
                    instructionBuffer.add(line);
                } catch (IOException | NullPointerException e){
                    break;
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex){
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
            System.out.println("\nMore info: ");
            ex.printStackTrace();
        }
        catch(IOException ex){
            System.out.println(
                    "Error reading file '" +
                            fileName + "'");
            System.out.println("\nMore info: ");
            ex.printStackTrace();
        }
    }

    public void registerView(Piano piano) {
        this.piano = piano;
    }

    @Override
    public void run(){
        for(String toDo : instructionBuffer){
            if(toDo.contains("\\")){
                break;
            }
            String actionCode = toDo.split("-")[0];
            String action = toDo.split("-")[1];

            switch (actionCode){
                case "0":
                    try {
                        sleep(Integer.parseInt(action));
                    } catch (InterruptedException ignore) {}
                    break;
                case "1":
                    currentNotes.put(action, new Note(PianoManager.getKeyCode(action)));
                    pianoController.drop(currentNotes.get(action));
                    break;
                case "2":

                    break;
                case "\\":

                    break;
            }
        }
    }
}
