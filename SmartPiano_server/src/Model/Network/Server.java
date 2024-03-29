package Model.Network;

import Controller.MainMenuController;
import Model.MainMenuManager;
import Model.ServerConfiguration;
import Model.User;
import Model.Utils.JsonServerUtils;
import View.MainMenu;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;

    private static ArrayList<DedicatedServer> dedicatedServers;
    private boolean isRunning;

    private MainMenuManager m;

    private HashMap<Integer, User> connectedUsers;
    public Server(){
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
        connectedUsers = new HashMap<>();
    }

    /**
     * Method that receives clients and creates dedicated servers for them.
     */
    @Override
    public void run(){

        SwingUtilities.invokeLater(() -> {
            MainMenuController c = new MainMenuController();
            m = new MainMenuManager(c);
            c.registerManager(m);
            MainMenu v = new MainMenu(c);
            c.registerView(v);
            v.setVisible(true);
        });

        try {
            ServerConfiguration sc = JsonServerUtils.getServerConfiguration("config");
            serverSocket = new ServerSocket(sc.getClientPort());
            isRunning = true;

            int i = 0;
            while (isRunning){
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                i++;
                DedicatedServer dServer = new DedicatedServer(socket, dedicatedServers, i, this);
                dedicatedServers.add(dServer);
                dServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tells the manager to refresh the UI.
     */
    public void refreshUI() {
        this.m.refreshUI(true);
    }

    public void registerConnectedUser(User user, int id) {
        connectedUsers.put(id, user);
    }

    public void deleteConnectedUser(int id) {
        connectedUsers.remove(id);
    }
}
