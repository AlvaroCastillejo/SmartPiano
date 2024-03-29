package Model.Network;

import Model.*;
import Model.Database.SQLOperations;
import Model.Database.SongVisualization;
import Model.Song_database;
import Model.Utils.Output;


import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class DedicatedServer extends Thread {
    private Server server;
    private User user;

    private ArrayList<DedicatedServer> dedicatedServers;
    private int id;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public DedicatedServer(Socket socket, ArrayList<DedicatedServer> dedicatedServers, int i, Server server) {
        this.server = server;
        try {
            this.socket = socket;
            this.dedicatedServers = dedicatedServers;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            this.id = i;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method in charg of receiving petitions and processing them.
     */
    @Override
    public void run(){
        try{
            while (true){
                // LOGIN/tryLogin
                String action = dis.readUTF();
                String[] elements = action.split("/");
                action = elements[1];
                String command = elements[0];
                Output.print(action, "green");
                System.out.println(command);
                switch (command){
                    case "LOGIN":
                        switch (action){
                            case "tryLogin":
                                sendAction("SEND_INFO/getLoggingUserCredentials");
                                String info = dis.readUTF();
                                String[] credentials = info.split("/");
                                User user = new User(credentials[0], credentials[1]);
                                if(credentials[0].contains("=") || credentials[0].contains("/") || credentials[0].contains("'")){
                                    sendAction("LOGIN/failed");
                                    break;
                                }
                                int status=SQLOperations.findUser(user);
                                switch (status){
                                    case 0:
                                        sendAction("LOGIN/logged");
                                        if (user.getUsername().contains("@")) {
                                            user.setUsername(SQLOperations.getUsernameFromEmail(user.getUsername()));
                                        }
                                        server.registerConnectedUser(user, this.id);
                                        this.user = user;

                                        break;
                                    case 1:
                                        sendAction("LOGIN/failed");
                                        break;
                                }
                                break;
                        }
                        break;
                    case "REGISTER":
                        switch (action){
                            case "tryRegister":
                                sendAction("SEND_INFO/getRegisterUserCredentials");
                                String info = dis.readUTF();
                                String[] credentials = info.split("/");
                                if(!credentials[2].equals(credentials[3])){
                                    sendAction("REGISTER/failed=3");
                                    break;
                                }
                                if(!credentials[1].contains("@")){
                                    sendAction("REGISTER/failed=4");
                                    break;
                                }
                                if(credentials[0].contains("@") || credentials[0].contains("/") || credentials[0].contains("=") || credentials[0].contains("'")){
                                    sendAction("REGISTER/failed=5");
                                    break;
                                }
                                if(credentials[1].contains("/") || credentials[1].contains("=") || credentials[1].contains("'")){
                                    sendAction("REGISTER/failed=5");
                                    break;
                                }
                                if(credentials[2].contains("@") || credentials[2].contains("/") || credentials[2].contains("=") || credentials[2].contains("'")){
                                    sendAction("REGISTER/failed=5");
                                    break;
                                }
                                User user = new User(credentials[0], credentials[1], credentials[2]);
                                int status=SQLOperations.userAlreadyExists(user);
                                switch (status){
                                    case 0:
                                        SQLOperations.ImportaUsuari(user.getUsername(),user.getPassword(),user.getMail());
                                        sendAction("REGISTER/registered");
                                        break;
                                    case 1:
                                        sendAction("REGISTER/failed=1");
                                        break;
                                    case 2:
                                        sendAction("REGISTER/failed=2");
                                        break;
                                }
                                break;
                        }
                        break;
                    case "DOWNLOAD":
                        String subAction = "";
                        if(action.contains("=")){
                            String[] aux = action.split("=");
                            action = aux[0];
                            subAction = aux[1];
                        }
                        switch (action){
                            case "accept":
                                sendAction("DOWNLOAD/accept?");
                                break;
                            case "userSongList":
                                sendAction("SEND_INFO/sendingSongListFromFromServerRequest");
                                String confirmation = dis.readUTF();
                                if(confirmation.equals("accept")){
                                    SongVisualization s = new SongVisualization();
                                    ArrayList<Song_database> songList = new ArrayList<>();
                                    songList = s.ShowSongListFrom(subAction);
                                    SongListToSend toSend = new SongListToSend(songList);

                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                    oos.writeObject(toSend);
                                }
                                break;
                            case "publicSongList":
                                sendAction("SEND_INFO/sendingSongListFromFromServerRequest");
                                confirmation = dis.readUTF();
                                if(confirmation.equals("accept")){
                                    //SongVisualization s = new SongVisualization();
                                    ArrayList<Song_database> songList = new ArrayList<>();
                                    songList = SQLOperations.getSongsFromUser(subAction);
                                    SongListToSend toSend = new SongListToSend(songList);

                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                    oos.writeObject(toSend);
                                }
                                break;
                            case "requestFileByName":
                                sendAction("SEND_INFO/sendingSongFileFromServerRequest");
                                String accept = dis.readUTF();

                                switch (accept){
                                    case "accept":
                                        File aux = new File("");
                                        String path = aux.getAbsolutePath();
                                        File file = new File(path + "\\SmartPiano_server\\src\\Model\\Assets\\Songs\\"+subAction+".mid");
                                        SongToSend songToSend = new SongToSend(file, subAction);

                                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                        oos.writeObject(songToSend);
                                        user.registerStartReproduction();

                                        SQLOperations.updateReproduction(subAction);
                                        break;
                                }
                                break;
                            case "friendListForUpdate":
                                sendAction("SEND_INFO/sendingFriendListForUpdate");
                                accept = dis.readUTF();
                                switch (accept){
                                    case "accept":
                                        ArrayList<Friend> friendList = SQLOperations.getFriendsFrom(user);
                                        FriendListToSend flts = new FriendListToSend();
                                        flts.setFriendList(friendList);
                                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                        oos.writeObject(flts);
                                        break;
                                }
                                break;
                        }
                        break;
                    case "SAVESONG":
                        switch (action){
                            case "saveSong":
                                sendAction("SEND_INFO/getSongInfo");
                                ObjectInputStream objectInputStream = new ObjectInputStream(dis);
                                SavedSong savedSong = null;
                                try {
                                    savedSong = (SavedSong) objectInputStream.readObject();
                                    sendAction("SEND_INFO/getSongFile");

                                    foo(savedSong.getSongName());

                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                SQLOperations.addSong(savedSong);
                                server.refreshUI();
                                break;
                        }
                    case "UPLOAD":
                        subAction = "";
                        if(action.contains("=")){
                            String[] aux = action.split("=");
                            action = aux[0];
                            subAction = aux[1];
                        }
                        switch (action){
                            case "friendRequest":
                                String friendCode = elements[2];
                                System.out.println("friendCode is: " + friendCode);
                                if(SQLOperations.userAlreadyExists(new User(friendCode, "")) == 0){
                                    sendAction("RECEIVE_INFO/friendAdded=false#notExist");
                                    break;
                                }
                                if(friendCode.equals(user.getUsername())){
                                    sendAction("RECEIVE_INFO/friendAdded=false#useruser");
                                } else {
                                    if(!SQLOperations.alreadyFriends(user, friendCode)){
                                        SQLOperations.addFriend(user, friendCode);
                                        sendAction("RECEIVE_INFO/friendAdded=true");
                                    } else {
                                        sendAction("RECEIVE_INFO/friendAdded=false#alreadyFriends");
                                    }
                                }
                                //Database stuff
                                //sendAction("DOWNLOAD/information/friendRequest/accepted");
                                break;
                            case "friendDeletion":
                                friendCode = subAction;
                                String userCode = this.user.getUsername();
                                if(friendCode.contains("@")){
                                    friendCode = SQLOperations.getUsernameFromEmail(friendCode);
                                }
                                System.out.println("Trying to delete " + friendCode + " from " + userCode);
                                SQLOperations.removeFriendFrom(friendCode, userCode);
                                sendAction("RECEIVE_INFO/friendDeleted=true");
                                break;
                            case "deleteAcc":
                                if (subAction.contains("@")) {
                                    subAction = (SQLOperations.getUsernameFromEmail(user.getUsername()));
                                }

                                SongVisualization s = new SongVisualization();
                                ArrayList<Song_database> songList = new ArrayList<>();
                                songList = s.ShowSongListFrom(this.user.getUsername());

                                for(Song_database i : songList){
                                    i.deleteFile();
                                }

                                SQLOperations.deleteAccount(this.user.getUsername());
                                break;
                            case "endReproduction":
                                user.registerEndReproduction();
                                SQLOperations.addMinutesPlayed(user);
                                server.refreshUI();
                                break;
                        }
                        break;

                    case "ASKFOR":
                        String[] aux = action.split("=");
                        switch(aux[0]){
                            case "checkSongName":
                                boolean exists = SQLOperations.checkSongName(aux[1]);
                                if (exists){
                                    sendAction("RECEIVE_INFO/checkSongName=true");
                                }else{
                                    sendAction("RECEIVE_INFO/checkSongName=false");
                                }
                                break;
                            case "friendList":
                                sendAction("SEND_INFO/sendingSongFromServerRequest");
                                String confirmation = dis.readUTF();
                                switch (confirmation){
                                    case "accept":
                                        ArrayList<Friend> friendList = SQLOperations.getFriendsFrom(user);
                                        FriendListToSend flts = new FriendListToSend();
                                        flts.setFriendList(friendList);
                                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                        oos.writeObject(flts);
                                        break;
                                    case "decline":
                                        //ignore for now
                                        break;
                                }
                                break;
                        }
                }
            }
        } catch (IOException | SQLException e) {
            if(e.getMessage().equals("Connection reset")){
                System.out.println("User " + user.getUsername() + " disconnected.");
                server.deleteConnectedUser(this.id);
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method that writes the bytes of a song into an actual file.
     * @param songName The name of the song.
     */
    private void foo(String songName) {
        try{
            InputStream is= socket.getInputStream();

            byte[] bytes= new byte[1024*16];

            DataInputStream dis=new DataInputStream(socket.getInputStream());
            String ext=dis.readUTF();

            File aux = new File("");
            String filePath = aux.getAbsolutePath();
            String path = filePath + "\\SmartPiano_server\\src\\Model\\Assets\\Songs\\" + songName + "." + ext;
            File f= new File(path);
            OutputStream output = new FileOutputStream(f);
            for(int i = is.read(bytes); i > 0; i--){
                output.write(bytes, 0, i);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAction(String action){
        try{
            System.out.println("ENVIO: " + action);
            dos.writeUTF(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
