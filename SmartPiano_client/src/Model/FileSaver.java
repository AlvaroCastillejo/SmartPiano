package Model;

import javax.swing.*;
import java.io.File;

public class FileSaver extends JFrame {

    public FileSaver(File f){

        LookAndFeel normal = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            f.renameTo(fileToSave);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }

        try {
            UIManager.setLookAndFeel(normal);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}
