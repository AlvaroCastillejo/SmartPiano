package Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileSaver extends JFrame {

    /**
     * Shows a dialog window to save a file in the PC.
     * @param f The file to save.
     */
    public FileSaver(File f){

        LookAndFeel normal = UIManager.getLookAndFeel();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setSelectedFile(new File("myfile.mid"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("midi file", "mid"));
        fileChooser.setDialogTitle("Specify a file to save");

        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            String filename = fileChooser.getSelectedFile().toString();
            if (!filename .endsWith(".mid"))
                filename += ".mid";

            File fileGenerated = new File(filename);
            f.renameTo(fileGenerated);
        }

        try {
            UIManager.setLookAndFeel(normal);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

    }
}
