package Model.Network;

import Controller.SaveSongController;
import Model.*;
import Model.Utils.JsonUtils;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

//A class that represents the client connected to the server.
public class Client extends Thread {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;

    private boolean isRunning;
    private int id;

    private LoginManager loginManager;
    private RegisterManager registerManager;
    private SaveSongManager saveSongManager;
    private PianoManager pianoManager;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private MenuManager menuManager;
    private FriendManager friendManager;

    /**
     * Class constructor. Initializes the communication with the server.
     * @param loginManager The LoginController to inform whether the user can login or not.
     * @throws IOException Because of the socket.
     */
    public Client(LoginManager loginManager) throws IOException {
        ServerConnectionConfiguration scc = JsonUtils.getConnectionConfiguration("config");

        this.loginManager = loginManager;
        socket = new Socket(scc.getIp(), scc.getPort());
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        id = -1;
    }

    /**
     * Assign the Register controller.
     * @param registerManager The RegisterController to inform whether the user can register or not.
     */
    public void assignRegisterController(RegisterManager registerManager){
        this.registerManager = registerManager;
    }

    public void assignSaveSongController(SaveSongManager saveSongManager){
        this.saveSongManager = saveSongManager;
    }
    /**
     * Start the communication with the server.
     */
    public void startServerConnection(){
        isRunning = true;
        start();
    }

    /**
     * Perpetual communication method. Working as the application is running.
     */
    @Override
    public void run(){
        try{
            while (isRunning){
                //Waits for the server to send information.
                String action = dis.readUTF();
                String[] elements = action.split("/");
                String command = elements[0];
                action = elements[1];
                switch (command){
                    case "SEND_INFO":
                        switch (action){
                            case "getLoggingUserCredentials":
                                dos.writeUTF(this.loginManager.getUserCredentials());
                                break;
                            case "getRegisterUserCredentials":
                                dos.writeUTF(this.registerManager.getRegisterCredentials());
                                break;
                            case "getSongInfo":
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(dos);
                                SavedSong savedSong = pianoManager.getSongFile();
                                File myFile = savedSong.getFile();
                                //savedSong.destroySongFile();
                                objectOutputStream.writeObject(savedSong);
                                break;
                            case "getSongFile":
                                File file = pianoManager.getSongFile().getFile();
                                String name = file.getName();
                                String n = name.substring(name.lastIndexOf(".") + 1);

                                OutputStream os =  socket.getOutputStream();

                                try {
                                    DataOutputStream dos= new DataOutputStream(os);
                                    dos.writeUTF(n);
                                    os.flush();


                                    FileInputStream fis= new FileInputStream(file);
                                    BufferedInputStream bis= new BufferedInputStream(fis);
                                    byte[] mybytearray = new byte[(int) file.length()];
                                    bis.read(mybytearray, 0, mybytearray.length);
                                    os.write(mybytearray, 0, mybytearray.length);
                                }

                                catch (Exception ex) {
                                    System.out.println(ex);
                                }
                                break;
                            case "sendingSongFromServerRequest":
                                sendAction("accept");
                                ObjectInputStream ois = new ObjectInputStream(dis);
                                FriendListToSend friendList = (FriendListToSend) ois.readObject();
                                menuManager.sendFriendListToSend(friendList);
                                break;
                            case "sendingSongListFromFromServerRequest":
                                sendAction("accept");
                                ois = new ObjectInputStream(dis);
                                Model.SongListToSend songListToSend = (SongListToSend) ois.readObject();
                                menuManager.showSongList(songListToSend.getSongs());
                        }
                        break;

                    case "RECEIVE_INFO":
                        switch (action){
                            case "checkSongName=true":
                                saveSongManager.sendResultCheckSongName(true);
                                break;
                            case "checkSongName=false":
                                saveSongManager.sendResultCheckSongName(false);
                                break;
                            case "friendAdded=true":
                                friendManager.sendResultFriendAdded("true/ignore");
                                break;
                            case "friendAdded=false#useruser":
                                friendManager.sendResultFriendAdded("false/useruser");
                                break;
                            case "friendAdded=false#alreadyFriends":
                                friendManager.sendResultFriendAdded("false/alreadyFriends");
                                break;
                        }
                        break;
                    case "LOGIN":
                        switch (action){
                            case "logged":
                                loginManager.logged(true);
                                break;
                            case "failed":
                                loginManager.showErrorUser("User or password is incorrect");
                                System.out.println(("EROR/USER MAL\n"));
                                loginManager.logged(false);
                                break;
                        }

                    case "REGISTER":
                        switch (action){
                            case "registered":
                                registerManager.registered(true);
                                break;
                            case "failed=1":
                                registerManager.showError("This user already exists");
                                registerManager.registered(false);
                                break;
                            case "failed=2":
                                registerManager.showError("This mail already exists");
                                registerManager.registered(false);
                                break;
                        }
                    case "SaveSong":
                        switch (action){
                            case "SaveSong":
                                System.out.println("VOY A GUARDAR LA CANCION\n");
                                break;
                        }
                }
            }
        } catch (IOException | ClassNotFoundException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void stopIt(){
        isRunning = false;
    }

    /**
     * Sends something to the server.
     * @param action The thing to be sent.
     */
    public void sendAction(String action){
        try{
            dos.writeUTF(action);
            System.out.println("ENVIO: " + action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return loginManager.getUserLogin();
    }

    public void setPianoManager(PianoManager pianoManager){
        this.pianoManager = pianoManager;
    }

    public void setSaveSongManager(SaveSongManager saveSongManager){
        this.saveSongManager = saveSongManager;
    }

    public void assignMenuManager(MenuManager m) {
        this.menuManager = m;
    }

    public void assignFriendManager(FriendManager m) {
        this.friendManager = m;
    }
}
