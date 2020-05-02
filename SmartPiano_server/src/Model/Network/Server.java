package Model.Network;

import Controller.MainMenuController;
import Model.MainMenuManager;
import Model.ServerConfiguration;
import Model.Utils.JsonServerUtils;
import View.MainMenu;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;

    private static ArrayList<DedicatedServer> dedicatedServers;
    private boolean isRunning;

    private MainMenuManager m;

    public Server(){
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
    }

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

    public void refreshUI() {
        this.m.refreshUI();
    }
}
