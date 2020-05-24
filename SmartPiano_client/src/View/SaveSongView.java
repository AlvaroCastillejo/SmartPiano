package View;

import View.CustomComponents.JPanelBackground;
import Controller.SaveSongController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class SaveSongView extends JFrame {
    private JPanelBackground jpBackground;
    private JTextField jtSongName; //Text Field for the song name
    private JTextField jtAlbumName; //Text Field for the song album
    private JComboBox<String> jcSongOptions;
    private JButton jbSaveSong;

    private int offset;

    public SaveSongView(){
        setTitle("Save Song");
        setSize(400, 350);
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


        JLabel JlSongName = new JLabel("Song name ");
        jpBackground.add(JlSongName);
        JlSongName.setLocation(50, 60);
        JlSongName.setSize(750, 14);

        jtSongName=new JTextField();
        jtSongName.setBounds(150,50,200,40);
        add(jtSongName);

        JLabel JlAlbumName = new JLabel("Album name");
        JlAlbumName.setLocation(50, 110);
        JlAlbumName.setSize(750, 14);
        jpBackground.add(JlAlbumName);

        jtAlbumName=new JTextField(); //Field for the album name
        jtAlbumName.setBounds(150,100,200,40);
        add(jtAlbumName);

        JLabel JlSongPrivacy = new JLabel("Song privacy");
        JlSongPrivacy.setLocation(50, 160);
        JlSongPrivacy.setSize(750, 14);
        jpBackground.add(JlSongPrivacy);

        jcSongOptions =new JComboBox<>();
        jcSongOptions.addItem("Public");
        jcSongOptions.addItem("Private");
        add(jcSongOptions);
        jcSongOptions.setBounds(150,150,100,40);


        jbSaveSong = new JButton("SAVE SONG");
        jbSaveSong.setBounds(100, 250+offset, 200, 50);
        jpBackground.add(jbSaveSong);


        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    public void calltoAction(ActionListener a){
        jbSaveSong.addActionListener(a);
        jbSaveSong.setActionCommand("saveSong");

    }

    public String getSongName() { return jtSongName.getText();}
    public String getAlbumName() {
        return jtAlbumName.getText();
    }
    public String getPrivacy() { return jcSongOptions.getSelectedItem().toString();}

    /**
     * Tells the user if the name was already in use for another song.
     */
    public void songNameAlreadyExists() {
        this.jtSongName.setBorder(BorderFactory.createLineBorder(Color.red));
    }
}
