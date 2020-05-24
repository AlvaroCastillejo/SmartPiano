package View;

import Controller.FriendController;
import Model.Friend;
import View.CustomComponents.FriendListScrollPane;
import View.CustomComponents.JPanelBackground;
import View.CustomComponents.JTextFieldHintUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FriendView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JTextField jtFriendCode;
    private JButton jbAddFriend;
    private JLabel jlStatus;

    private JPanel listPanel;

    private int offset;
    private FriendController controller;

    public FriendView (ArrayList<Friend> friendList, FriendController controller, Point locationOnScreen) {
        this.controller = controller;
        setTitle("Friends");
        setSize(500, 500);
        //setLocationRelativeTo(null);
        setLocation(locationOnScreen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }

        jlStatus = new JLabel();
        jlStatus.setBounds(120, 60, 200, 100);
        getContentPane().add(jlStatus);

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\friends.jpg"));

        jbBack = new JButton(); //back to main menu
        setButtonInvisible(jbBack);
        jbBack.setBounds(40, 45+offset, 75, 45);
        jpBackground.add(jbBack);

        jtFriendCode = new JTextField(); //to type the friend code
        jtFriendCode.setUI(new JTextFieldHintUI("Type friends' name...", Color.gray));
        jtFriendCode.setBounds(120, 130+offset, 150, 45);
        jtFriendCode.setOpaque(false);
        jtFriendCode.setBorder(null);
        jpBackground.add(jtFriendCode);

        jbAddFriend = new JButton(); //button to add a friend with the previous code
        setButtonInvisible(jbAddFriend);
        jbAddFriend.setBounds(295, 130+offset, 60, 40);
        jpBackground.add(jbAddFriend);

        listPanel = createFriendList(friendList);
        listPanel.setBounds(70, 220, 350, 210);
        getContentPane().add(listPanel);


        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    /**
     * Creates the friendList from a given list.
     * @param friendList The list of friends.
     * @return The panel containing the list.
     */
    public JPanel createFriendList(ArrayList<Friend> friendList) {
        JPanel toReturn = new JPanel();
        toReturn.setLayout(null);
        toReturn.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        FriendListScrollPane friendListScrollPane = new FriendListScrollPane(friendList, 350, 210, controller);
        friendListScrollPane.setBounds(0, 0, friendListScrollPane.getPanelWidth(), friendListScrollPane.getPanelHeight());
        toReturn.add(friendListScrollPane);

        return toReturn;
    }

    /**
     * Updated the friend list with the new one.
     * @param friendList The new friend list.
     */
    public void updateUI(ArrayList<Friend> friendList){
        getContentPane().remove(listPanel);
        listPanel.removeAll();

        listPanel = createFriendList(friendList);
        listPanel.setBounds(70, 220, 350, 210);
        getContentPane().add(listPanel);

        getContentPane().repaint();
        //getContentPane().revalidate();
    }

    public void registerController (FriendController al) {
        this.controller = al;
        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");

        //pasar aqui el contenido del textField con el codigo del amigo
        jbAddFriend.addActionListener(al);
        jbAddFriend.setActionCommand("AddFriend");
    }

    /**
     * Turns the JButton invisible.
     * @param button The button to make invisible.
     */
    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getJtFriendCode() {
        return jtFriendCode.getText();
    }

    /**
     * Tells the view the status of the friend addition.
     * @param message The status.
     * @param color The color of the status.
     */
    public void addedStatus(String message, String color) {
        jlStatus.setText(message);
        if(color.equals("green")) jlStatus.setForeground(Color.GREEN);
        else jlStatus.setForeground(Color.RED);
        repaint();
    }

    /**
     * Tells the view the status of the friend deletion.
     * @param message The status.
     * @param color The color of the status.
     */
    public void deletedStatus(String message, String color) {
        jlStatus.setText(message);
        if(color.equals("green")) jlStatus.setForeground(Color.GREEN);
        else jlStatus.setForeground(Color.RED);
        repaint();
    }
}
