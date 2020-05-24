package Model;

import Controller.MainMenuController;
import Model.Database.SQLOperations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class MainMenuManager {
    private final MainMenuController c;
    private double[] hoursReproductions;

    public MainMenuManager(MainMenuController c) {
        this.c = c;
        hoursReproductions = new double[24];

        Arrays.fill(hoursReproductions, 0);
    }

    /**
     * Refresh the UI in order to show the changes.
     * @param updateReproductions Tells if this refresh is supposed to update the reproductions value.
     */
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

    /**
     * Deletes a song from the database and refreshed the UI.
     * @param songID The song ID to delete.
     */
    public void deleteSong(String songID) {
        SQLOperations.deleteSong(songID);
        this.refreshUI(false);
    }
}
