package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private String username;
    private String mail;
    private String password;
    private long minutesPlayed;
    private long startReproduce;
    private long endReproduce;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String mail, String password){
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void registerStartReproduction() {
        startReproduce = System.currentTimeMillis();

    }

    public void registerEndReproduction() {
        endReproduce = System.currentTimeMillis();
        minutesPlayed = (endReproduce - startReproduce)/60000;
    }

    public float getMinutes() {
        return minutesPlayed;
    }
}
