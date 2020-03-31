package Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooser extends JFrame{

    public File FileChooser(){

        String filename = "";

        LookAndFeel normal = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) { }

        do {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
            //fileChooser.setSelectedFile(new File("myfile.mid"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("midi file", "mid"));
            fileChooser.setDialogTitle("Specify a song to play");

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                filename = fileChooser.getSelectedFile().toString();
                if (!filename.endsWith(".mid")) {
                    JFrame error = new JFrame();
                    JOptionPane jOptionPane = new JOptionPane("Error");
                    JOptionPane.showMessageDialog(this, "Must select a .mid file!");
                    error.getContentPane().add(jOptionPane);
                } else {
                    break;
                }
            } else {
                break;
            }
        } while (true);

        try {
            UIManager.setLookAndFeel(normal);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        return new File(filename);
    }
}
