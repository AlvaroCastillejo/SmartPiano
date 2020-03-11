package View.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class JPiano extends JPanel {
    public JPiano() {
        //no funciona el JPanel..
        JLayeredPane teclat = new JLayeredPane();

        // total de tecles
        JButton[] tecla = new JButton[24];
        int sum = 0, i;
        //las teclas normals
        for (i = 0; i < 14; i++) {
            tecla[sum] = crearTeclaNormal(i);
            teclat.add(tecla[sum], 0, -1);
            sum += 1;
            if (i % 7 != 2 && i % 7 != 6) {
                tecla[sum] = crearTeclaSust(i);
                teclat.add(tecla[sum], 1, -1);
                sum += 1;
            }
        }

        teclat.setSize(500,300);
        teclat.setLocation(450,800);


        this.add(teclat);
        //setLocationRelativeTo(null);
        setSize(900, 800);
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private JButton crearTeclaNormal(int i)
    {
        JButton teclaB = new JButton();
        teclaB.setBackground(Color.WHITE);
        //teclaB.setBorderPainted(true);
        teclaB.setOpaque(true);
        teclaB.setLocation(i*40,0);
        teclaB.setSize(40, 150);
        return teclaB;
    }

    private JButton crearTeclaSust(int i)
    {
        JButton teclaN = new JButton();
        teclaN.setBackground(Color.BLACK);
        teclaN.setBorderPainted(false);
        teclaN.setOpaque(true);
        teclaN.setLocation(25 + i*40,0);
        teclaN.setSize(30, 90);

        return teclaN;
    }
}
