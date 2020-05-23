package Model;

import Controller.MainMenuController;
import Model.Database.SQLOperations;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainMenuManager {
    private final MainMenuController c;
    private double[] hoursReproductions;

    public MainMenuManager(MainMenuController c) {
        this.c = c;
        hoursReproductions = new double[24];
        /*for(int i = 0; i < hoursReproductions.length; i++){
            hoursReproductions[i] = i;
        }*/
        Arrays.fill(hoursReproductions, 0);
    }

    public void refreshUI(boolean updateReproductions) {
        if(updateReproductions){
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            hoursReproductions[hour] += 1;
        }
        c.refreshUI(hoursReproductions);
    }

    public void deleteSong(String songID) {
        SQLOperations.deleteSong(songID);

        this.refreshUI(false);
    }
}
