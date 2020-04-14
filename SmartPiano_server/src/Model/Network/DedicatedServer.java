package Model.Network;

import Model.Database.SQLOperations;
import Model.LoginManager;
import Model.RegisterManager;
import Model.User;
import Model.Utils.Output;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
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
                Output.print(action, "green");
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

                        break;
                    case "UPLOAD":
                        switch (action){
                            case "friendRequest":
                                String friendCode = elements[2];
                                System.out.println("friendCode is: " + friendCode);
                                break;
                        }
                        break;
                }
            }
        } catch (IOException | SQLException e) {
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
