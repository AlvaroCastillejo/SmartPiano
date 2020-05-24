package View;

import Controller.MainMenuController;
import Model.Database.SongVisualization;
import Model.Song_database;
import View.CustomComponents.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainMenu extends JFrame {

    private int offset;
    private MainMenuController controller;

    private JPanel panel1, panel2, panel3;
    private JTabbedPane tabs;

    private JPanel subPanel1, subPanel2;
    private JTabbedPane subtabs;
    private List<Double> graphPointValues;

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
        double[] hoursReproductions = new double[24];
        Arrays.fill(hoursReproductions, 0);
        panel2 = createGraphicsPane(hoursReproductions);
        panel3 = createTop5Pane();

        //Añadimos un nombre de la pestaña y el panel
        tabs.addTab("Manage Songs", panel1);
        tabs.addTab("Graphics", panel2);
        tabs.addTab("Top 5", panel3);

        getContentPane().add(tabs);
    }

    /**
     * Creates the panel containing the TOP 5 most popular songs.
     * @return Returns the panel created.
     */
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

        for(int i = 0; i < 5; i++){
            try{
                topSongList.get(i);
            } catch (IndexOutOfBoundsException e){
                topSongList.add(new Song_database());
            }
        }


        String[] columnNames = {"Id", "Title", "Author", "Album", "Nº Reproductions"};
        Object[][] data = {
                {topSongList.get(0).getSong_id(), topSongList.get(0).getSong_name(), topSongList.get(0).getAuthor_name(), topSongList.get(0).getAlbum_id(), topSongList.get(0).getNum_reproductions()},
                {topSongList.get(1).getSong_id(), topSongList.get(1).getSong_name(), topSongList.get(1).getAuthor_name(), topSongList.get(1).getAlbum_id(), topSongList.get(1).getNum_reproductions()},
                {topSongList.get(2).getSong_id(), topSongList.get(2).getSong_name(), topSongList.get(2).getAuthor_name(), topSongList.get(2).getAlbum_id(), topSongList.get(2).getNum_reproductions()},
                {topSongList.get(3).getSong_id(), topSongList.get(3).getSong_name(), topSongList.get(3).getAuthor_name(), topSongList.get(3).getAlbum_id(), topSongList.get(3).getNum_reproductions()},
                {topSongList.get(4).getSong_id(), topSongList.get(4).getSong_name(), topSongList.get(4).getAuthor_name(), topSongList.get(4).getAlbum_id(), topSongList.get(4).getNum_reproductions()}
        };

        JTable table = createJTable(columnNames, data);

        //JTable table = new JTable(data, columnNames);
        table.setBounds(0, 20, 800, 600);
        JTableHeader h = table.getTableHeader();
        h.setBounds(0, 0, 800, 20);

        //table.setModel(new DefaultTableModel());

        generalPanel.add(table);
        generalPanel.add(h);

        return generalPanel;
    }

    /**
     * Creates the JTable conatining the TOP 5.
     * @param header The header of the table.
     * @param rows The rows of the table.
     * @return The table created.
     */
    private JTable createJTable(String[] header, Object[][] rows){
        JTable table = new JTable();
        DefaultTableModel tablemodel = (DefaultTableModel) table.getModel();
        tablemodel.setRowCount(0);
        for (String col : header) {
            tablemodel.addColumn(col);
        }
        for (Object[] row : rows) {
            tablemodel.addRow(row);
        }
        table.setModel(tablemodel);
        return table;
    }

    /**
     * Creates the Graphics panel.
     * @param hoursReproductions The array that contains the information of the reproductions by hours.
     * @return The panel generated.
     */
    private JPanel createGraphicsPane(double[] hoursReproductions) {
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);

        //Componentes del panel2
        //Create the subtabs
        subtabs = new JTabbedPane();
        subtabs.setBounds(0,0,800,600);

        //Create the panels and add them to the tabs.
        subPanel1 = createAndShowGraph(true, hoursReproductions);
        subPanel2 = createAndShowGraph(false, hoursReproductions);

        //Añadimos un nombre de la pestaña y el panel
        subtabs.addTab("Evolution of reproductions", subPanel1);
        subtabs.addTab("Evolution of minutes played", subPanel2);

        panel2.add(subtabs);
        return panel2;
    }

    /**
     * Creates teh graph.
     * @param isReproductionsGraph Tells if the graph to generate is about the minutes played or the number of reproductions.
     * @param hoursReproductions The array that contains the information of the reproductions by hours.
     * @return The panel created.
     */
    private JPanel createAndShowGraph(boolean isReproductionsGraph, double[] hoursReproductions) {
        JPanel subPanel = new JPanel();
        List<Double> graphValues = new ArrayList<>();
        Random random;
        Double newValue;
        if (isReproductionsGraph) {
            random = new Random();
            //newValue = reproductions
        } else {
            //newValue = minutes = controller.getNEwGraphPoint
            random = new Random();
        }

        int maxDataPoints = 40;
        int maxScore = 10;
        LocalDateTime dateTime = LocalDateTime.now();
        int currentHour = dateTime.getHour();
        //aqui ja hauries de tenir els graph values anteriors a la current hour plens
        // nomes hauries de fer un add del newValue
        //graphValues.add(newValue);

        for (int i = 0; i <= currentHour; i++) {
            graphValues.add((double) hoursReproductions[i]);
            //graphValues.add((double) i);
        }

        //GraphPane graphPane = new GraphPane(isReproductionsGraph, graphValues);
        GraphPane graphPane = new GraphPane(isReproductionsGraph, graphValues);
        graphPane.setPreferredSize(new Dimension(780, 490));

        subPanel.add(graphPane);
        return subPanel;
    }

    /**
     * Creates the Manage songs pane.
     * @return The panel created.
     */
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

        JLabel nMinutes = new JLabel("Album");
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

    /**
     * Refreshes the UI.
     * @param hoursReproductions The array that contains the information of the reproductions by hours.
     */
    public void refresh(double[] hoursReproductions){
        this.panel1 = createManageSongsPane();
        this.panel2 = createGraphicsPane(hoursReproductions);
        this.panel3 = createTop5Pane();

        getContentPane().removeAll();
        tabs.removeAll();

        tabs.addTab("Manage Songs", panel1);
        tabs.addTab("Graphics", panel2);
        tabs.addTab("Top 5", panel3);

        refreshGraph(hoursReproductions);

        getContentPane().add(tabs);

        getContentPane().repaint();
        getContentPane().revalidate();
    }

    /**
     * Method that updates the graphs.
     * @param hoursReproductions The array that contains the information of the reproductions by hours.
     */
    public void refreshGraph(double[] hoursReproductions) {
        this.subPanel1 = createAndShowGraph(true, hoursReproductions);
        this.subPanel2 = createAndShowGraph(false, hoursReproductions);

        panel2.removeAll();
        subtabs.removeAll();

        //Añadimos un nombre de la pestaña y el panel
        subtabs.addTab("Evolution of reproductions", subPanel1);
        subtabs.addTab("Evolution of minutes played", subPanel2);

        panel2.add(subtabs);
        panel2.repaint();
        panel2.revalidate();
    }

    public void setGraphPointValues(List<Double> graphPointValues) {
        this.graphPointValues = graphPointValues;
    }

    public List<Double> getGraphPointValues() {
        return graphPointValues;
    }
}
