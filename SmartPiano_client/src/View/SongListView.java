package View;

import Controller.SongListController;
import Model.Song_database;
import View.CustomComponents.JPanelBackground;
import View.CustomComponents.SongScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongListView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JButton jbSong;
    private JButton jbPlaySong;
    private JPanel songListPanel;
    private int offset;

    private SongListController controller;

    public SongListView (ArrayList<Song_database> songList, SongListController controller) {
        this.controller = controller;

        setTitle("Song list");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\song-list.jpg"));

        jbBack = new JButton(); //back to main menu or to friends
        setButtonInvisible(jbBack);
        jbBack.setBounds(40, 45+offset, 75, 45);
        jpBackground.add(jbBack);

        songListPanel = createManageSongsPane(songList);
        songListPanel.setBounds(40, 150, 400, 200);
        getContentPane().add(songListPanel);

        jbPlaySong = new JButton(); //Play song button
        setButtonInvisible(jbPlaySong);
        jbPlaySong.setBounds(130, 380+offset, 235, 45);
        jpBackground.add(jbPlaySong);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    private JPanel createManageSongsPane(ArrayList<Song_database> songList) {
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        SongScrollPane panel = new SongScrollPane(songList, 400, 200, controller);
        panel.setBounds(0, 0, panel.getPanelWidth(), panel.getPanelHeight());
        panel1.add(panel);

        return panel1;
    }

    public void registerController (ActionListener al) {
        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");

        jbPlaySong.addActionListener(al);
        jbPlaySong.setActionCommand("PlaySong");
    }

    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
