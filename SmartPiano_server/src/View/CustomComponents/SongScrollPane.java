package View.CustomComponents;

import Controller.MainMenuController;
import Model.Database.Song_database;
import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SongScrollPane extends JPanel {
    JPanel jpLower;
    JScrollPane jspSongs;
    int width, height;
    private Map<String, JButton> buttonMap;

    public SongScrollPane(ArrayList<Song_database> songs, int width, int height, ActionListener controller){
        this.width = width;
        this.height = height;

        buttonMap = new HashMap<>();

        super.setLayout(null);
        super.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        jpLower = new JPanel();
        jpLower = new JPanel(new GridLayout(songs.size(), 1));
        jspSongs = new JScrollPane(jpLower);
        jspSongs.setBounds(0, 0, width, height);
        //jspSongs.setBorder(BorderFactory.createTitledBorder("All Subjects"));

        JPanel auxPanel;
        JLabel auxLabel;
        for (Song_database t : songs) {
            auxPanel = new JPanel();
            auxPanel.setLayout(new GridLayout(1,4));

            auxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "/"));

            JLabel title = new JLabel(t.getSong_name());
            title.setVerticalAlignment(JLabel.CENTER);
            title.setHorizontalAlignment(JLabel.CENTER);
            auxPanel.add(title);

            JLabel author = new JLabel(t.getAlbum_id());
            author.setHorizontalAlignment(JLabel.CENTER);
            author.setVerticalAlignment(JLabel.CENTER);
            auxPanel.add(author);

            JLabel album = new JLabel(t.getAlbum_id());
            album.setHorizontalAlignment(JLabel.CENTER);
            album.setVerticalAlignment(JLabel.CENTER);
            auxPanel.add(album);

            JButton jbDelete = new JButton("Delete");
            jbDelete.addActionListener(controller);
            jbDelete.setActionCommand("DELETE/".concat(String.valueOf(t.getSong_id())));
            jbDelete.setHorizontalAlignment(JLabel.CENTER);
            jbDelete.setVerticalAlignment(JLabel.CENTER);
            buttonMap.put(String.valueOf(t.getSong_id()), jbDelete);

            auxPanel.add(jbDelete);

            jpLower.add(auxPanel);
        }
        super.add(jspSongs);
    }

    public int getPanelHeight() {
        return this.height;
    }

    public int getPanelWidth() {
        return this.width;
    }
}
