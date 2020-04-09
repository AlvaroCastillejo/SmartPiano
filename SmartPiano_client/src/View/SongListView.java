package View;

import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class SongListView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JButton jbSong;
    private JButton jbPlaySong;
    private int offset;

    public SongListView () {
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

        jbSong = new JButton("Song"); //Song name button(dynamic array with all songs)
        //setButtonInvisible(jbSong);
        jbSong.setBounds(55, 150+offset, 370, 30);
        jpBackground.add(jbSong);

        jbPlaySong = new JButton(); //Play song button
        setButtonInvisible(jbPlaySong);
        jbPlaySong.setBounds(130, 380+offset, 235, 45);
        jpBackground.add(jbPlaySong);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    public void registerController (ActionListener al) {
        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");

        jbSong.addActionListener(al);
        jbSong.setActionCommand("SelectedSong");

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
