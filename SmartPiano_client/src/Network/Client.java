package Network;

import Controller.LoginController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;

    private boolean isRunning;
    private int id;

    private LoginController controller;

    private DataOutputStream dos;
    private DataInputStream dis;


    public Client(LoginController controller) throws IOException {
        this.controller = controller;
        Socket socket = new Socket(IP, PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        id = -1;
    }

    public void startServerConnection(){
        isRunning = true;
        start();
    }

    @Override
    public void run(){
        try{
            while (isRunning){
                String action = dis.readUTF();
                String[] elements = action.split("/");
                String command = elements[0];
                action = elements[1];
                switch (command){
                    case "SEND_INFO":
                        switch (action){
                            case "getLoggingUserCredentials":
                                dos.writeUTF(this.controller.getUserCredentials());
                                break;
                        }
                        break;
                    case "LOGIN":
                        switch (action){
                            case "logged":
                                System.out.println("LOGGED!!!!!!!");
                                controller.logged(true);
                                break;
                            case "failed":
                                System.out.println("NOOOOOO");
                                controller.logged(false);
                                break;
                        }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopIt(){
        isRunning = false;
    }

    public void sendAction(String action){
        try{
            dos.writeUTF(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
