package View.CustomComponents;

import Model.Friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendListScrollPane extends JPanel {
    JPanel jpLower;
    JScrollPane jspSongs;
    int width, height;
    private Map<String, JButton> buttonMap;

    public FriendListScrollPane(ArrayList<Friend> friends, int width, int height, ActionListener controller){
        setBackground(Color.DARK_GRAY);
        this.width = width;
        this.height = height;

        buttonMap = new HashMap<>();

        super.setLayout(null);
        super.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        jpLower = new JPanel();
        jpLower = new JPanel(new GridLayout(friends.size(), 1));
        jspSongs = new JScrollPane(jpLower);
        jspSongs.setBounds(0, 0, width, height);

        JPanel auxPanel;
        JLabel auxLabel;
        for (Friend t : friends) {
            auxPanel = new JPanel();
            auxPanel.setLayout(new GridLayout(1,4));

            auxPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "/"));

            JLabel title = new JLabel(t.getName());
            title.setVerticalAlignment(JLabel.CENTER);
            title.setHorizontalAlignment(JLabel.CENTER);
            auxPanel.add(title);

            JLabel author = new JLabel(t.getName());
            author.setHorizontalAlignment(JLabel.CENTER);
            author.setVerticalAlignment(JLabel.CENTER);
            auxPanel.add(author);

            JButton jbSelectFriend = new JButton("->");
            jbSelectFriend.addActionListener(controller);
            jbSelectFriend.setActionCommand("SELECT/".concat(t.getId()));
            buttonMap.put(t.getId(), jbSelectFriend);
            auxPanel.add(jbSelectFriend);

            JButton jbDelete = new JButton("Delete");
            jbDelete.addActionListener(controller);
            jbDelete.setActionCommand("DELETE/".concat(t.getId()));
            jbDelete.setHorizontalAlignment(JLabel.CENTER);
            jbDelete.setVerticalAlignment(JLabel.CENTER);
            buttonMap.put(t.getId(), jbDelete);
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
