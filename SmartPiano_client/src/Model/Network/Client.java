package Model.Network;

import Model.LoginManager;
import Model.RegisterManager;
import Model.ServerConnectionConfiguration;
import Model.Utils.JsonUtils;

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

    private LoginManager loginManager;
    private RegisterManager registerManager;

    private DataOutputStream dos;
    private DataInputStream dis;

    /**
     * Class constructor. Initializes the communication with the server.
     * @param loginManager The LoginController to inform whether the user can login or not.
     * @throws IOException Because of the socket.
     */
    public Client(LoginManager loginManager) throws IOException {
        ServerConnectionConfiguration scc = JsonUtils.getConnectionConfiguration("config");

        this.loginManager = loginManager;
        Socket socket = new Socket(scc.getIp(), scc.getPort());
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
                        }
                        break;
                    case "LOGIN":
                        switch (action){
                            case "logged":
                                loginManager.logged(true);
                                break;
                            case "failed":
                                loginManager.showErrorUser("User or password is incorrect");
                                loginManager.logged(false);
                                break;
                        }
                        break;


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