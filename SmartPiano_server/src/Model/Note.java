package Model;

import java.io.Serializable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//A class that will represent a note to be played.
public class Note extends Thread implements Serializable {

    private long time_on;
    private long time_off;
    private String name;
    private long goTime;
    private Song song;
    private CyclicBarrier gate;

    public Note(long time_on, String noteName) {
        this.time_on = time_on;
        this.name = noteName;
        this.goTime = 0;
    }

    public long getTime_on() {
        return time_on;
    }

    public void setTime_on(long time_on) {
        this.time_on = time_on;
    }

    public long getTime_off() {
        return time_off;
    }

    public void setTime_off(long time_off) {
        this.time_off = time_off;
    }

    public String getNoteName() {
        return name;
    }

    public void setNoteName(String name) {
        this.name = name;
    }

    public int getPixelSize() {
        return (int)((time_off)-(time_on))/10;
    }

    public void setGoTime (long time){
        this.goTime = time;
    }

    public void registerSong(Song song){
        this.song = song;
    }

    public void registerGate(CyclicBarrier gate){
        this.gate = gate;
    }

    @Override
    public void run(){
        try {
            gate.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        try {
            sleep(goTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        song.dropNote(this);
        System.out.println("Drop: " + name);
    }
}
