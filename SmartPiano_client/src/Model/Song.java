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
    private LinkedList<Note> notes;
    private PianoController piano;

    private LinkedList<Note> threadedNotes;

    /**
     * The class constructor. Plays the song by dropping the notes. Yet to be fully implemented.
     * @param notes A list with all the notes of the song.
     * @param c The controller of the piano.
     */
    public Song(LinkedList<Note> notes, PianoController c){
        this.notes = notes;
        this.piano = c;

        //Crear tantos threads como notas tengan que caer.
    }

    @Override
    public void run(){
        final CyclicBarrier gate = new CyclicBarrier(notes.size()+1);

        for(Note note : notes){
            note.registerSong(this);
            note.setGoTime(note.getTime_on() + 5000);
            note.registerGate(gate);
        }
        for(Note note :notes){
            note.start();
        }

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
