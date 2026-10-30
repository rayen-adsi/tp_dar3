package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Serveur en attente d'un client...");

        Socket socket = serverSocket.accept();
        System.out.println("Un client est connecté");

        socket.close();
        serverSocket.close();
    }
}