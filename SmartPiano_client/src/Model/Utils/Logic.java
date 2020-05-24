package Model.Utils;

import Model.Song_database;

public class Logic {
    /**
     * Given an array it reverts it.
     * @param a The array to revert.
     * @param n The size of the array.
     * @return The reversed array.
     */
    public static Song_database[] reverseSongList(Song_database a[], int n)
    {
        Song_database[] b = new Song_database[n];
        int j = n;
        for (int i = 0; i < n; i++) {
            b[j - 1] = a[i];
            j = j - 1;
        }

        return b;
    }
}
