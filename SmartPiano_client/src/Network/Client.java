package Network;

import Controller.LoginController;
import Controller.RegisterController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;

    private boolean isRunning;
    private int id;

    private LoginController loginController;
    private RegisterController registerController;

    private DataOutputStream dos;
    private DataInputStream dis;


    public Client(LoginController loginController) throws IOException {
        this.loginController = loginController;
        Socket socket = new Socket(IP, PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        id = -1;
    }

    public void assignRegisterController(RegisterController registerController){
        this.registerController = registerController;
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
                                dos.writeUTF(this.loginController.getUserCredentials());
                                break;
                            case "getRegisterUserCredentials":
                                dos.writeUTF(this.registerController.getRegisterCredentials());
                                break;
                        }
                        break;
                    case "LOGIN":
                        switch (action){
                            case "logged":
                                System.out.println("LOGGED!!!!!!!");
                                loginController.logged(true);
                                break;
                            case "failed":
                                System.out.println("NOOOOOO");
                                loginController.logged(false);
                                break;
                        }
                        break;


                    case "REGISTER":
                        switch (action){
                            case "registered":
                                System.out.println("You are now one of us!");
                                registerController.registered(true);
                                break;
                            case "failed=1":
                                System.out.println("This user already exists");
                                registerController.registered(false);
                                break;
                            case "failed=2":
                                System.out.println("This mail already exists");
                                registerController.registered(false);
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
