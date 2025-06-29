package pl.umcs.oop;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Server server;

    public Socket getSocket() {
        return socket;
    }

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);

            String message;
            // Czytaj wiadomości od klienta aż do końca połączenia
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);
                send("Echo: " + message);

                if ("quit".equalsIgnoreCase(message.trim())) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Client disconnected unexpectedly: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    public void send(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }

    private void cleanup() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.removeClient(this);
        System.out.println("Client thread finished");
    }
}