package View;

import Controller.FriendController;
import Model.Friend;
import View.CustomComponents.FriendListScrollPane;
import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class FriendView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JTextField jtFriendCode;
    private JButton jbAddFriend;
    private JLabel jlStatus;

    private int offset;
    private FriendController controller;

    public FriendView (ArrayList<Friend> friendList, FriendController controller) {
        this.controller = controller;
        setTitle("Friends");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        if(System.getProperty("os.name").equals("mac")){
            offset = 10;
        } else {
            offset = 0;
        }

        jlStatus = new JLabel();
        jlStatus.setBounds(200, 50, 200, 100);
        getContentPane().add(jlStatus);

        jpBackground = new JPanelBackground();
        jpBackground.setLayout(null);
        String f = new File("").getAbsolutePath();
        jpBackground.setBackground(f.concat("\\SmartPiano_client\\images\\friends.jpg"));

        jbBack = new JButton(); //back to main menu
        setButtonInvisible(jbBack);
        jbBack.setBounds(40, 45+offset, 75, 45);
        jpBackground.add(jbBack);

        jtFriendCode = new JTextField("type new friends' code..."); //to type the friend code
        jtFriendCode.setBounds(120, 130+offset, 150, 45);
        jtFriendCode.setOpaque(false);
        jtFriendCode.setBorder(null);
        jpBackground.add(jtFriendCode);

        jbAddFriend = new JButton(); //button to add a friend with the previous code
        setButtonInvisible(jbAddFriend);
        jbAddFriend.setBounds(295, 130+offset, 60, 40);
        jpBackground.add(jbAddFriend);

        FriendListScrollPane friendListScrollPane = new FriendListScrollPane(friendList, 350, 210, controller);
        friendListScrollPane.setBounds(70, 220, friendListScrollPane.getPanelWidth(), friendListScrollPane.getPanelHeight());
        getContentPane().add(friendListScrollPane);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
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
     * @param button
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

    public void addedStatus(String message, String color) {
        jlStatus.setText(message);
        if(color.equals("green")) jlStatus.setBackground(Color.GREEN);
        else jlStatus.setBackground(Color.RED);
        repaint();
    }
}
