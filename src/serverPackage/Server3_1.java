package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3_1 {
    public static void main(String[] args) {
        int numeroClient = 1;

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur en attente ...");

            while (true) {
                Socket socket = serverSocket.accept();

                ClientHandler3_1 handler = new ClientHandler3_1(socket, numeroClient);
                Thread thread = new Thread(handler);
                thread.start();

                System.out.println("Client n°" + numeroClient + " accepté .");
                numeroClient++;
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}