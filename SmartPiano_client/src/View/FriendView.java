package View;

import View.CustomComponents.JPanelBackground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class FriendView extends JFrame {
    private JPanelBackground jpBackground;
    private JButton jbBack;
    private JTextField jtFriendCode;
    private JButton jbAddFriend;
    private JButton jbFriend;
    private JButton jbShowFriendSongs;
    private JButton jbDeleteFriend;
    private int offset;

    public FriendView () {
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

        jbFriend = new JButton("Friend"); // friend's name button (dynamic array with all friends)
        //setButtonInvisible(jbAddFriend);
        jbFriend.setBounds(55, 210+offset, 280, 30);
        jpBackground.add(jbFriend);

        //hacer un boton por cada amigo y cuando se pulse, mostrar los dos botones siguientes
        jbShowFriendSongs = new JButton(">"); //Show songs list button
        //setButtonInvisible(jbShowFriendSongs);
        jbShowFriendSongs.setBounds(335, 210+offset, 45, 30);
        jpBackground.add(jbShowFriendSongs);

        jbDeleteFriend = new JButton("F"); //Delete Friend button
        //setButtonInvisible(jbDeleteFriend);
        jbDeleteFriend.setBounds(380, 210+offset, 45, 30);
        jpBackground.add(jbDeleteFriend);

        getContentPane().add(jpBackground, BorderLayout.CENTER);
    }

    public void registerController (ActionListener al) {
        jbBack.addActionListener(al);
        jbBack.setActionCommand("Back");

        //pasar aqui el contenido del textField con el codigo del amigo
        jbAddFriend.addActionListener(al);
        jbAddFriend.setActionCommand("AddFriend");

        jbFriend.addActionListener(al);
        jbFriend.setActionCommand("Friend");

        //quiero usar la misma vista para las canciones del usuario y del amigo seleccionado
        //pero para mostrar la lista de canciones necesito pasarle el nombre del usuario al que pertenecen
        jbShowFriendSongs.addActionListener(al);
        jbShowFriendSongs.setActionCommand("ShowSongs");

        jbDeleteFriend.addActionListener(al);
        jbDeleteFriend.setActionCommand("DeleteFriend");
    }

    private void setButtonInvisible(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public String getJtFriendCode() {
        return jtFriendCode.getText();
    }
}
