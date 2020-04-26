package View;

import Controller.MainMenuController;
import Model.Song;
import View.CustomComponents.SongScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class MainMenu extends JFrame {
    private int offset;
    private JPanel jpLower;
    private JScrollPane jspSongs;
    private MainMenuController controller;



    public MainMenu(MainMenuController a){
        this.controller = a;

        setTitle("Server Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) { }

        //Create the tabs
        JTabbedPane tabs = new JTabbedPane();

        //Create the panels and add them to the tabs.
        //JPanel panel1 = createManageSongsPane();
        //LinkedList<Song> songs = new LinkedList<>();
        //songs = initializeSongs();
        JPanel panel1 = createManageSongsPane();
        JPanel panel2 = createGraphicsPane();
        JPanel panel3 = createTop5Pane();


        //Añadimos un nombre de la pestaña y el panel
        tabs.addTab("Manage Songs", panel1);
        tabs.addTab("Graphics", panel2);
        tabs.addTab("Top 5", panel3);

        getContentPane().add(tabs);

    }

    private JPanel createTop5Pane() {
        JPanel panel3 = new JPanel();
        //Componentes del panel3
        JLabel et_p3 = new JLabel("Estas en el panel 3");
        panel3.add(et_p3);
        return panel3;
    }

    private JPanel createGraphicsPane() {
        JPanel panel2 = new JPanel();
        //Componentes del panel2
        JLabel et_p2 = new JLabel("Estas en el panel 2");
        panel2.add(et_p2);
        return panel2;
    }

    private JPanel createManageSongsPane() {
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel header = new JPanel(new GridLayout(1, 4));

        JLabel title = new JLabel("Title");
        title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        header.add(title);

        JLabel author = new JLabel("Author");
        author.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        author.setHorizontalAlignment(JLabel.CENTER);
        author.setVerticalAlignment(JLabel.CENTER);
        header.add(author);

        JLabel nMinutes = new JLabel("Nº Minutes");
        nMinutes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nMinutes.setHorizontalAlignment(JLabel.CENTER);
        nMinutes.setVerticalAlignment(JLabel.CENTER);
        header.add(nMinutes);

        JLabel nPlays = new JLabel("Nº Plays");
        nPlays.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nPlays.setHorizontalAlignment(JLabel.CENTER);
        nPlays.setVerticalAlignment(JLabel.CENTER);
        header.add(nPlays);

        header.setBounds(20, 40, 500, 25);
        panel1.add(header);

        LinkedList<Song> songs = initializeSongs();
        SongScrollPane panel = new SongScrollPane(songs, 500, 300, controller);
        panel.setBounds(20, 65, panel.getPanelWidth(), panel.getPanelHeight());
        panel1.add(panel);

        return panel1;
    }

    private LinkedList<Song> initializeSongs() {
        LinkedList<Song> songLinkedList = new LinkedList<>();
        songLinkedList.add(new Song("Julia Michels", "0"));
        songLinkedList.add(new Song("Adam Levine", "1"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        songLinkedList.add(new Song("Ed Sheeran", "2"));
        return songLinkedList;
    }

}
