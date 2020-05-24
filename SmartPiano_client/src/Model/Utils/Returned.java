package Model.Utils;

import Model.Song_database;

public class Returned {
    private Song_database[] a;
    private int s;
    private int t;

    public Returned(Song_database[] a, int i, int j) {
        this.a = a;
        this.s = i;
        this.t = j;
    }

    public Song_database[] getA() {
        return a;
    }

    public void setA(Song_database[] a) {
        this.a = a;
    }

    public int getS() {
        return s;
    }

    public void setS(int i) {
        this.s = i;
    }

    public int getT() {
        return t;
    }

    public void setT(int j) {
        this.t = j;
    }
}
