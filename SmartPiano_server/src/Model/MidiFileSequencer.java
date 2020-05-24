package Model;

import javax.sound.midi.*;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MidiFileSequencer {
    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};

    public static LinkedList<Note> notes;
    public static Map<String, Note> notes_on;
    public static Map<String, Note> notes_off;

    /**
     * Method that processes a MIDI file to get the notes.
     * @param fileName The MIDI file.
     * @return A linkedList with the extracted notes.
     * @throws Exception If any error in the midi file sequence.
     */
    public static LinkedList<Note> getNotes(String fileName) throws Exception {
        notes = new LinkedList<>();
        notes_on = new HashMap<>();
        notes_off = new HashMap<>();

        Sequence sequence = MidiSystem.getSequence(new File(fileName));

        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12);
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();

                        notes_on.put(noteName, new Note(event.getTick(), noteName + octave));

                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();

                        try {
                            notes_on.get(noteName).setTime_off(event.getTick());
                            notes.add(notes_on.get(noteName));
                            notes_on.remove(noteName);

                            System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                        } catch (NullPointerException ignore){

                        }
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }
        return notes;
    }
}
