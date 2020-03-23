package Network;

import Controller.LoginController;
import Controller.RegisterController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//A class that represents the client connected to the server.
public class Client extends Thread {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;

    private boolean isRunning;
    private int id;

    private LoginController loginController;
    private RegisterController registerController;

    private DataOutputStream dos;
    private DataInputStream dis;

    /**
     * Class constructor. Initializes the communication with the server.
     * @param loginController The LoginController to inform whether the user can login or not.
     * @throws IOException Because of the socket.
     */
    public Client(LoginController loginController) throws IOException {
        this.loginController = loginController;
        Socket socket = new Socket(IP, PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        dis = new DataInputStream(socket.getInputStream());
        id = -1;
    }

    /**
     * Assign the Register controller.
     * @param registerController The RegisterController to inform whether the user can register or not.
     */
    public void assignRegisterController(RegisterController registerController){
        this.registerController = registerController;
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
                                loginController.logged(true);
                                break;
                            case "failed":
                                loginController.logged(false);
                                break;
                        }
                        break;


                    case "REGISTER":
                        switch (action){
                            case "registered":
                                registerController.registered(true);
                                break;
                            case "failed=1":
                                registerController.showError("This user already exists");
                                registerController.registered(false);
                                break;
                            case "failed=2":
                                registerController.showError("This mail already exists");
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

    /**
     * Sends something to the server.
     * @param action The thing to be sent.
     */
    public void sendAction(String action){
        try{
            dos.writeUTF(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
