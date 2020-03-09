package Network;

import Model.LoginManager;
import Model.RegisterManager;
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
                //REGISTER/tryRegister
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
                    case "REGISTER":
                        switch (action){
                            case "tryRegister":
                                sendAction("SEND_INFO/getRegisterUserCredentials");
                                String info = dis.readUTF();
                                String[] credentials = info.split("/");
                                User r = new User(credentials[0], credentials[1], credentials[2]);
                                if(RegisterManager.userAlreadyRegistered(r) == 0){
                                    sendAction("REGISTER/registered");
                                } else {
                                    sendAction("REGISTER/failed=".concat(String.valueOf(RegisterManager.userAlreadyRegistered(r))));
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
