package Model;

import Controller.PianoController;
import View.Piano;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//A class that represents a song to be played in the piano.
public class Song extends Thread {
    private static final int COUNTDOWN = 5;
    private LinkedList<Note> notes;
    private PianoController piano;

    //***********
    private String songName;
    private String songId;

    //***********

    private LinkedList<Note> threadedNotes;

    //***********
    public String getSongName() {
        return songName;
    }

    public String getSongId() {
        return songId;
    }

    public Song(String name, String id){
        this.songName = name;
        this.songId = id;
    }
    //***********

    /**
     * The class constructor. Plays the song by dropping the notes. Yet to be fully implemented.
     * @param notes A list with all the notes of the song.
     * @param c The controller of the piano.
     */
    public Song(LinkedList<Note> notes, PianoController c){
        this.notes = notes;
        this.piano = c;
        this.threadedNotes = new LinkedList<>();
        //Crear tantos threads como notas tengan que caer.
    }

    @Override
    public void run(){
        int countdownMs = COUNTDOWN*1000;
        final CyclicBarrier gate = new CyclicBarrier(notes.size()+1);

        for(Note note : notes){
            note.registerSong(this);
            note.setGoTime(note.getTime_on() + countdownMs);
            note.registerGate(gate);
            threadedNotes.add(note);
        }
        for(Note note :notes){
            note.start();
        }

        piano.startCount(COUNTDOWN);

        try {
            gate.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    public void dropNote(Note note) {
        piano.drop(note);
    }
}
