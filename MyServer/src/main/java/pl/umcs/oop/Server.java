package pl.umcs.oop;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ClientThread currentClient = null;
    private boolean isClientConnected = false;

    public ClientThread getCurrentClient() {
        return currentClient;
    }

    public boolean isClientConnected() {
        return isClientConnected;
    }

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                System.out.println("Waiting for client connection...");
                clientSocket = serverSocket.accept();

                if (isClientConnected) {
                    System.out.println("Client already connected. Rejecting new connection.");
                    clientSocket.close();
                    continue;
                }

                System.out.println("Client connected: " + clientSocket.getInetAddress());
                isClientConnected = true;
                currentClient = new ClientThread(clientSocket, this);
                currentClient.start();

                currentClient.join();

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public synchronized void removeClient(ClientThread client) {
        if (currentClient == client) {
            currentClient = null;
            isClientConnected = false;
            System.out.println("Client disconnected. Ready for new connection.");
        }
    }

    public void shutdown() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}