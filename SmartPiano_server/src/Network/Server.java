package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private static final int PORT = 12345;
    private ServerSocket serverSocket;

    private static ArrayList<DedicatedServer> dedicatedServers;
    private boolean isRunning;

    public Server(){
        dedicatedServers = new ArrayList<>();
        serverSocket = null;
    }

    @Override
    public void run(){
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;

            int i = 0;
            while (isRunning){
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                i++;
                DedicatedServer dServer = new DedicatedServer(socket, dedicatedServers, i);
                dedicatedServers.add(dServer);
                dServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
