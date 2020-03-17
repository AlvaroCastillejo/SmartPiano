package View;

import Controller.MenuController;

import View.CustomComponents.JPanelBackground;
import View.CustomComponents.JPiano;
import Controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenuView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbPlayPiano;
    private JButton jbRecordSong;
    private JButton jbPlaySong;
    private JButton jbFriends;
    private JButton jbConfiguration;


    private int offset;

    public MainMenuView(){
        setTitle("Menu");
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
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\menu.png"));

        //CREATE BUTTONS
        jbPlayPiano = new JButton(); //Play piano button
        //setButtonInvisible(jbPlayPiano);
        jbPlayPiano.setBounds(100, 160+offset, 285, 45);
        jpBackground.add(jbPlayPiano);

        jbRecordSong = new JButton(); //Record a song button
        //setButtonInvisible(jbRecordSong);
        jbRecordSong.setBounds(100, 220+offset, 285, 45);
        jpBackground.add(jbRecordSong);

        jbPlaySong = new JButton(); //Play song button
        //setButtonInvisible(jbLogin);
        jbPlaySong.setBounds(100, 280+offset, 285, 45);
        jpBackground.add(jbPlaySong);

        jbFriends = new JButton(); //Friends button
        //setButtonInvisible(jbLogin);
        jbFriends.setBounds(100, 340+offset, 285, 45);
        jpBackground.add(jbFriends);

        jbConfiguration = new JButton("config"); //Configuration button
        jpBackground.add(jbConfiguration);
        jbConfiguration.setBounds(360, 25+offset, 75, 45);

        getContentPane().add(jpBackground, BorderLayout.CENTER);

    }

    public void registerController(ActionListener al){
        jbPlayPiano.addActionListener(al);
        jbPlayPiano.setActionCommand("PlayPiano");

        jbConfiguration.addActionListener(al);
        jbConfiguration.setActionCommand("Configuration");

    }

    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }



}
