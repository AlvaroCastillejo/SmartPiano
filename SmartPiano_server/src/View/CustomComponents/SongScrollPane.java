package View.CustomComponents;

import Controller.MainMenuController;
import Model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SongScrollPane extends JPanel {
    JPanel jpLower;
    JScrollPane jspSongs;
    int width, height;
    private Map<String, JButton> buttonMap;

    public SongScrollPane(LinkedList<Song> songs, int width, int height, ActionListener controller){
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
        for (Song t : songs) {
            auxPanel = new JPanel();
            auxPanel.setLayout(new GridLayout(1,4));


            auxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "/"));


            JButton jbAddToFav = new JButton("’Add to favorites");
            jbAddToFav.addActionListener(controller);
            jbAddToFav.setActionCommand("LSUBJECTS/add&".concat(t.getSongId()));
            buttonMap.put(t.getSongId(), jbAddToFav);

            auxPanel.add(new JLabel(t.getSongName()));
            auxLabel = new JLabel(t.getSongId() + " credits");
            auxPanel.add(auxLabel);
            auxPanel.add(jbAddToFav);

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
