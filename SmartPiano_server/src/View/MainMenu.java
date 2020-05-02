package View;

import Controller.MainMenuController;
import Model.Database.SongVisualization;
import Model.Database.Song_database;
import Model.Song;
import View.CustomComponents.SongScrollPane;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainMenu extends JFrame {

    private int offset;
    private MainMenuController controller;

    private JPanel panel1, panel2, panel3;
    private JTabbedPane tabs;

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
        tabs = new JTabbedPane();

        //Create the panels and add them to the tabs.
        panel1 = createManageSongsPane();
        panel2 = createGraphicsPane();
        panel3 = createTop5Pane();

        //Añadimos un nombre de la pestaña y el panel
        tabs.addTab("Manage Songs", panel1);
        tabs.addTab("Graphics", panel2);
        tabs.addTab("Top 5", panel3);

        getContentPane().add(tabs);
    }

    private JPanel createTop5Pane() {
        JPanel generalPanel = new JPanel();

        generalPanel.setLayout(null);
        generalPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        SongVisualization s = new SongVisualization();
        ArrayList<Song_database> topSongList = new ArrayList<>();
        try {
            topSongList = s.ShowTop5List();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"Id", "Title", "Author", "Album", "Nº Reproductions"};
        Object[][] data = {
                {topSongList.get(0).getSong_id() , topSongList.get(0).getSong_name(), topSongList.get(0).getAuthor_name(), topSongList.get(0).getAlbum_id() , topSongList.get(0).getNum_reproductions()},
                {topSongList.get(1).getSong_id() , topSongList.get(1).getSong_name(), topSongList.get(1).getAuthor_name(), topSongList.get(1).getAlbum_id() , topSongList.get(1).getNum_reproductions()},
                {topSongList.get(2).getSong_id() , topSongList.get(2).getSong_name(), topSongList.get(2).getAuthor_name(), topSongList.get(2).getAlbum_id() , topSongList.get(2).getNum_reproductions()},
                {topSongList.get(3).getSong_id() , topSongList.get(3).getSong_name(), topSongList.get(3).getAuthor_name(), topSongList.get(3).getAlbum_id() , topSongList.get(3).getNum_reproductions()},
                {topSongList.get(4).getSong_id() , topSongList.get(4).getSong_name(), topSongList.get(4).getAuthor_name(), topSongList.get(4).getAlbum_id() , topSongList.get(4).getNum_reproductions()}
        };

        JTable table = new JTable(data, columnNames);
        table.setBounds(0, 20, 800, 600);
        JTableHeader h = table.getTableHeader();
        h.setBounds(0, 0, 800, 20);

        generalPanel.add(table);
        generalPanel.add(h);

        return generalPanel;
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

        header.setBounds(0, 0, 500, 25);
        panel1.add(header);

        SongVisualization s = new SongVisualization();
        ArrayList<Song_database> songList = new ArrayList<>();
        try {
            songList = s.ShowSongList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SongScrollPane panel = new SongScrollPane(songList, 500, 510, controller);
        panel.setBounds(0, 25, panel.getPanelWidth(), panel.getPanelHeight());
        panel1.add(panel);

        return panel1;
    }

    public void refresh(){
        this.panel1 = createManageSongsPane();
        this.panel2 = createGraphicsPane();
        this.panel3 = createTop5Pane();

        getContentPane().removeAll();
        tabs.removeAll();

        tabs.add(panel1);
        tabs.add(panel2);
        tabs.add(panel3);

        getContentPane().add(tabs);

        getContentPane().repaint();
        getContentPane().revalidate();
    }
}
