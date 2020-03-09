package Network;

import Model.LoginManager;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServer extends Thread {
    private ArrayList<DedicatedServer> dedicatedServers;
    private int id;

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    public DedicatedServer(Socket socket, ArrayList<DedicatedServer> dedicatedServers, int i) {
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

    @Override
    public void run(){
        try{
            while (true){
                String action = dis.readUTF();
                String[] elements = action.split("/");
                action = elements[1];
                String command = elements[0];
                //LOGIN/tryLogin
                switch (command){
                    case "LOGIN":
                        switch (action){
                            case "tryLogin":
                                sendAction("SEND_INFO/getLoggingUserCredentials");
                                String info = dis.readUTF();
                                if(LoginManager.checkLoging(new User(info.split("/")[0], info.split("/")[1]))){
                                    sendAction("LOGIN/logged");
                                } else {
                                    sendAction("LOGIN/failed");
                                }
                                break;
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAction(String action){
        try{
            dos.writeUTF(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
