package Model;

//A class that will represent a note to be played.
public class Note {

    private long time_on;
    private long time_off;
    private String name;

    public Note(long time_on) {
        this.time_on = time_on;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
