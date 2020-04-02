package View;

import Controller.PianoController;
import Model.Configuration;
import Model.Note;
import Model.PianoManager;
import Model.Song;
import View.CustomComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Piano extends JFrame {
    private PianoController pianoController;

    //Array with the piano keys.
    private JButton[] keys;
    //Map with the piano keys with its codes.
    private Map<String, JButton> keyboardMap;

    //Info on the key binding.
    private JLabel recording;
    private JLabel goBack;
    private JLabel jlToggleAutoPlay;
    //Rec indicator.
    private RoundedPanel rec;

    //Info on the current autoplay state.
    private JLabel jtBtoggleAutoplay;

    //Its currently recording a song.
    private Boolean isRecording;
    //Its the "play a song" piano.
    private Boolean isSongPlayer;
    //Its the "record a song" piano.
    private Boolean isRecordingPianoType;
    //If the user wants autoplay or not.
    private Boolean wantsAutoplay;

    //Stores the Notes that are being built.
    private Map<String, Thread> ascendingNotes;
    private Map<String, Thread> fallingNotes;

    /**
     * The constructor for the Piano. It builds the simplest piano, without any information or control.
     * @param a The controller for the Piano.
     * @param toPlay The song to play if needed.
     */
    public Piano(PianoController a, Song toPlay) {
        this.pianoController = a;
        keyboardMap = new HashMap<>();

        ascendingNotes = new HashMap<>();
        fallingNotes = new HashMap<>();

        boolean isRunning = false;
        this.isSongPlayer = false;
        this.isRecordingPianoType = false;
        this.wantsAutoplay = true;

        setSize(856, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setResizable(false);

        JLayeredPane keyBoard = new JLayeredPane();

        //Total amount of keys.
        keys = new JButton[36];
        int j = 0;
        //The normal keys.
        for (int i = 0; i < 21; i++) {
            keys[j] = generateKey(i);
            keyBoard.add(keys[j], 0, -1);
            j += 1;
            if (i % 7 != 2 && i % 7 != 6) {
                keys[j] = generateSustKey(i);
                keyBoard.add(keys[j], 1, -1);
                j += 1;
            }
        }

        keyBoard.setSize(840,300);
        keyBoard.setLocation(0,550);

        //Beautifiers.
        JPanel horizontalDivider = new JPanel();
        horizontalDivider.setSize(840, 7);
        horizontalDivider.setLocation(0, 541);
        horizontalDivider.setBackground(new Color(39, 39, 39));

        JPanel bottomWood = new JPanel();
        bottomWood.setSize(840, 11);
        bottomWood.setLocation(0, 750);
        bottomWood.setBackground(new Color(39, 39, 39));

        JPanel upperWood = new JPanel();
        upperWood.setSize(840, 20);
        upperWood.setLocation(0, 0);
        upperWood.setBackground(new Color(39, 39, 39));

        JPanel redPad = new JPanel();
        redPad.setSize(840,3);
        redPad.setLocation(0,548);
        redPad.setBackground(new Color(140, 0, 25));

        JPanel verticalDivider1 = new JPanel();
        verticalDivider1.setSize(2, 541);
        verticalDivider1.setLocation(120, 0);
        verticalDivider1.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider2 = new JPanel();
        verticalDivider2.setSize(2, 541);
        verticalDivider2.setLocation(280, 0);
        verticalDivider2.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider3 = new JPanel();
        verticalDivider3.setSize(2, 541);
        verticalDivider3.setLocation(400, 0);
        verticalDivider3.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider4 = new JPanel();
        verticalDivider4.setSize(2, 541);
        verticalDivider4.setLocation(560, 0);
        verticalDivider4.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider5 = new JPanel();
        verticalDivider5.setSize(2, 541);
        verticalDivider5.setLocation(680, 0);
        verticalDivider5.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider6 = new JPanel();
        verticalDivider6.setSize(2, 541);
        verticalDivider6.setLocation(838, 0);
        verticalDivider6.setBackground(new Color(149, 149, 149));

        JPanel verticalDivider7 = new JPanel();
        verticalDivider7.setSize(2, 541);
        verticalDivider7.setLocation(0, 0);
        verticalDivider7.setBackground(new Color(149, 149, 149));

        getContentPane().add(keyBoard);
        getContentPane().add(redPad);
        getContentPane().add(horizontalDivider);
        getContentPane().add(bottomWood);
        getContentPane().add(upperWood);
        getContentPane().add(verticalDivider1);
        getContentPane().add(verticalDivider2);
        getContentPane().add(verticalDivider3);
        getContentPane().add(verticalDivider4);
        getContentPane().add(verticalDivider5);
        getContentPane().add(verticalDivider6);
        getContentPane().add(verticalDivider7);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        //toPlay.registerView(this);
        //toPlay.start();
    }

    /**
     * Generates normal key.
     * @param i The index of the key.
     * @return The key generated.
     */
    private JButton generateKey(int i){
        JButton key = new JButton();
        key.setBackground(Color.WHITE);
        key.setOpaque(true);
        key.setLocation(i*40,0);
        key.setSize(40, 200);

        //Disable space bar triggering click for JButton.
        key.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        key.addActionListener(pianoController);
        key.setActionCommand("w/".concat(String.valueOf(i)));
        key.addKeyListener(pianoController);

        keyboardMap.put("w/".concat(String.valueOf(i)), key);

        return key;
    }

    /**
     * Generates sustained key.
     * @param i The index of the key.
     * @return The key generated.
     */
    private JButton generateSustKey(int i){
        JButton sustKey = new JButton();
        sustKey.setBackground(Color.BLACK);
        sustKey.setOpaque(true);
        sustKey.setLocation(25 + i*40,0);
        sustKey.setSize(30, 125);

        //Disable space bar triggering click for JButton.
        sustKey.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
        sustKey.addActionListener(pianoController);
        sustKey.setActionCommand("b/".concat(String.valueOf(i)));
        sustKey.addKeyListener(pianoController);

        keyboardMap.put("b/".concat(String.valueOf(i)), sustKey);

        return sustKey;
    }

    /**
     * Makes a piano key look like its pressed by changing its colour.
     * @param a The code of the key to press.
     */
    public void pressButton(String a){
        try{
            if(a.startsWith("b")) ((JButton) keyboardMap.get(a)).setBackground(Color.DARK_GRAY);
            else ((JButton) keyboardMap.get(a)).setBackground(Color.LIGHT_GRAY);
        } catch (NullPointerException ignore){}
    }

    /**
     * Makes a piano key look like its released by changing its colour.
     * @param a The code of the key to release.
     */
    public void releaseButton(String a){
        try{
            if(a.startsWith("b")) ((JButton) keyboardMap.get(a)).setBackground(Color.BLACK);
            else ((JButton) keyboardMap.get(a)).setBackground(Color.WHITE);
        } catch (NullPointerException ignore){}
    }

    /**
     * This method drops a note from the top of the piano.
     * @param fallingNote The note to drop.
     */
    public void drop(Note fallingNote) {
        //Each falling note is a new thread.
        Thread playing = new Thread(new Runnable() {
            @Override
            public void run() {
                //Set the Y position where it isn't visible, above the top.
                int y = -fallingNote.getPixelSize();
                //Get the X position of the note.
                int x = keyboardMap.get(PianoManager.getKeyCode(fallingNote.getNoteName())).getX();

                RoundedPanel note;
                do{
                    note = new RoundedPanel();
                    note.setSize(35, fallingNote.getPixelSize());
                    //"y" increments by one in each iteration.
                    note.setLocation(x+2, y);
                    //Set the colour depending if it's sustained or not.
                    if(PianoManager.getKeyCode(fallingNote.getNoteName()).startsWith("b")){
                        note.setBackground(new Color(88, 111, 192));
                        note.grabFocus();
                    } else {
                        note.setBackground(new Color(88, 135, 192));
                    }
                    getContentPane().add(note);
                    repaint();
                    y++;
                    boolean tmp = wantsAutoplay;

                    //If the note hits the keys of the piano and the user wants the autoplay.
                    if(hit(note) && wantsAutoplay){
                        //tmp = true;
                        //Get the pane of the keyboard.
                        JLayeredPane jLayeredPane = (JLayeredPane) getContentPane().getComponentAt(note.getX() + 5, note.getY()+note.getHeight()+20);
                        //From that pane get the button below the note.
                        JButton source = (JButton) jLayeredPane.getComponentAt(note.getX() + 5, 10);
                        //Emulate that is the user who presses the button by generating a KeyEvent.
                        pianoController.keyPressed(new KeyEvent(source, 1, System.currentTimeMillis(), InputEvent.BUTTON1_DOWN_MASK, Configuration.getKeyNameFromKeyEventCode(PianoManager.getKeyCode(fallingNote.getNoteName())), 'i'));
                    }
                    //If the note ended.
                    if(hitnt(note)){
                        //tmp = false;
                        JLayeredPane jLayeredPane = (JLayeredPane) getContentPane().getComponentAt(note.getX() + 5, note.getY()+20);
                        JButton source = (JButton) jLayeredPane.getComponentAt(note.getX() + 5, 10);
                        pianoController.keyReleased(new KeyEvent(source, 1, System.currentTimeMillis(), InputEvent.ALT_DOWN_MASK, Configuration.getKeyNameFromKeyEventCode(PianoManager.getKeyCode(fallingNote.getNoteName())), 'i'));
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getContentPane().remove(note);
                } while (!onBottom(note));
            }

            private boolean hitnt(RoundedPanel note) {
                return (note.getY() == 541);
            }

            private boolean hit(RoundedPanel note) {
                return (note.getY()+note.getHeight()) == 541;
            }

            private boolean onBottom(JPanel note) {
                return note.getY() == 541;
            }
        });

        //Register in the map tha falling note.
        fallingNotes.put(fallingNote.getNoteName(), playing);
        playing.start();
    }

    /**
     * Settles everything that needs the Recording Piano, such as the rec panel or the info to be displayed.
     */
    public void isRecordingPiano(){

        this.isRecordingPianoType = true;

        //Info
        recording = new JLabel("Press " + pianoController.getRecordingKey() + " to start/stop recording");
        recording.setBounds(150 ,90, 600, 100);
        recording.setFont(new Font("Tahoma", Font.BOLD, 30));
        getContentPane().add(recording);

        //Info
        goBack = new JLabel("Press " + pianoController.getReturnKey() + " to quit playing");
        goBack.setBounds(150, 130, 600, 100);
        goBack.setFont(new Font("Tahoma", Font.BOLD, 30));
        getContentPane().add(goBack);

        rec = new RoundedPanel();
        rec.setShady(true);
        rec.setBackground(Color.RED);
        rec.setBounds((856/2)-25-25, 20, 25 ,25);
        rec.grabFocus();
        getContentPane().add(rec);

        RoundedPanel upperContainer = new RoundedPanel();
        upperContainer.setShady(false);
        upperContainer.setBackground(new Color(39, 39, 39));
        upperContainer.setBounds((856/2)-70, 10, 100, 40);
        getContentPane().add(upperContainer);

        getContentPane().repaint();
    }

    /**
     * This only makes the REC dot blink.
     */
    public void startRecording(){
        Thread recordingThread  = new Thread(new Runnable() {
            @Override
            public void run() {
                isRecording = true;
                recording.setVisible(false);
                goBack.setVisible(false);
                while (isRecording){
                    try {
                        //Thread.sleep(10);
                        rec.setVisible(false);
                        getContentPane().repaint();
                        Thread.sleep(1000);
                        rec.setVisible(true);
                        getContentPane().repaint();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        recordingThread.start();
    }

    /**
     * Updates the state of the view to not recording.
     */
    public void stopRecording(){
        isRecording = false;
        recording.setVisible(true);
        goBack.setVisible(true);
    }

    /**
     * Check if the piano is currently recording.
     * @return If it's recording.
     */
    public Boolean isRecording() {
        return isRecording;
    }

    /**
     * Check if the tipe of the piano is the "Recording Piano".
     * @return If is the recording piano.
     */
    public Boolean getIsRecordingPianoType(){
        return isRecordingPianoType;
    }

    /**
     * Creates the ascending note when the user is recording.
     * @param ascendingNote The code of the key to create.
     */
    public synchronized void ascend(String ascendingNote) {
        //Each ascending note is a new Thread.
        Thread playing = new Thread(new Runnable() {
            @Override
            public void run() {
                //If the note hasn't reached the top isRunning = true.
                boolean isRunning = true;
                //If the note is being created (cropped = false) or if the note is already cut off and is ascending (cropped = true)
                boolean croped = false;

                //Get the position of the note.
                int x = keyboardMap.get(ascendingNote).getX();

                //i = 541 to start from the bottom.
                int i = 541;
                //Initial size of the note.
                int j = 0;
                //Will always be running unless the note displayed is out of view.
                while (isRunning){
                    try {
                        RoundedPanel note = new RoundedPanel();
                        note.setSize(35,j);
                        note.setLocation(x+2,i);
                        //Assign different colours if the note is sustained or not. (b/... -> sustained(black) || (w/... -> not sustained(white)))
                        if(ascendingNote.startsWith("b")){
                            note.setBackground(new Color(88, 111, 192));
                            note.grabFocus();
                        } else {
                            note.setBackground(new Color(88, 135, 192));
                        }
                        getContentPane().add(note);
                        getContentPane().repaint();
                        //Determines the speed of the ascending notes. 10ms means 5s to get to the top. Ascends 1 pixel for each 10ms.
                        Thread.sleep(10);
                        getContentPane().remove(note);
                        i--;

                        //If there is another note displaying in the same column (including itself) in this moment.
                        if(!(ascendingNotes.get(ascendingNote) == null)){
                            //And its not cropped yet, means it's himself.
                            if(!croped){
                                //Increment the size by one pixel.
                                j++;
                            }
                        } else {
                            //If there is NOT another note in this same moment it must have been cropped so the size should not change.
                            croped = true;
                        }

                        //If the bottom of the note reaches the top stop the Thread as it is not visible no more. This way we save computational cost.
                        if(onTop(note)){
                            isRunning = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            private boolean onTop(JPanel note) {
                return note.getY()+note.getHeight() == 0;
            }
        });
        //Hardcoded to 1. Should be the keyCode
        ascendingNotes.put(ascendingNote, playing);
        playing.start();
    }

    /**
     * Crop a note by removing it from the ascendingNotes map.
     * @param note The keyCode of the note to cut off.
     */
    public void cropAscendingNote(String note){
        ascendingNotes.remove(note);
    }

    /**
     * Settles everything that needs the Play a Song Piano.
     */
    public void isSongPiano() {
        this.isSongPlayer = true;

        jlToggleAutoPlay = new JLabel("Press " + pianoController.getRecordingKey() + " to toggle AutoPlay");
        jlToggleAutoPlay.setBounds(150 ,90, 600, 100);
        jlToggleAutoPlay.setFont(new Font("Tahoma", Font.BOLD, 30));
        getContentPane().add(jlToggleAutoPlay);

        jtBtoggleAutoplay = new JLabel("autoplay: " + wantsAutoplay);
        jtBtoggleAutoplay.setBounds(10, 30, 150, 20);
        jtBtoggleAutoplay.setFont(new Font("Tahoma", Font.BOLD, 20));
        getContentPane().add(jtBtoggleAutoplay);

        repaint();
    }

    /**
     * Check if the Piano is a Play a Song Piano type.
     * @return If it's Play a Song.
     */
    public Boolean getIsSongPlayer(){
        return isSongPlayer;
    }

    /**
     * Starts the countdown.
     * @param countdown Countdown seconds.
     */
    public void startCountDown(int countdown) {
        int originalCountdown = countdown;
        final int[] countdownThread = {countdown};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < originalCountdown; i++){
                    JLabel jlCountdown = new JLabel(String.valueOf(countdownThread[0]));
                    jlCountdown.setBounds(350 ,90, 600, 300);
                    jlCountdown.setFont(new Font("Tahoma", Font.BOLD, 200));
                    getContentPane().add(jlCountdown);
                    repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getContentPane().remove(jlCountdown);
                    repaint();
                    countdownThread[0]--;
                }

                jlToggleAutoPlay.setVisible(false);

                JLabel jlCountdown = new JLabel("Let's play!");
                jlCountdown.setBounds(150,90, 600, 300);
                jlCountdown.setFont(new Font("Tahoma", Font.BOLD, 100));
                getContentPane().add(jlCountdown);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getContentPane().remove(jlCountdown);
                repaint();
            }
        });

        thread.start();

    }

    public boolean wantsAutoPlay() {
        return wantsAutoplay;
    }

    /**
     * Switches the autoplay to on/off.
     * @param b The new autoplay value.
     */
    public void setAutoPlay(boolean b) {
        wantsAutoplay = b;
        jtBtoggleAutoplay.setText("autoplay: " + wantsAutoplay);
        repaint();
    }
}