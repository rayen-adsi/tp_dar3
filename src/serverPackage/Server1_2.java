package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_2 {
    public static void main(String[] args) throws IOException {
        // socket d'ecoute pour cree une liaison entre serveur et client
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Je suis un serveur en attente la connexion d'un client ");
        // socket client : socket qui contient les donnees vient du requete client
        Socket socket = serverSocket.accept();
        System.out.println("un client est connecté");

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        DataInputStream dis = new DataInputStream(is);
        DataOutputStream dos = new DataOutputStream(os);
        int nb = dis.readInt();
        System.out.println("Le serveur a reçu le nombre " + nb);
        while (nb != 0)
        {
            int x = nb * 5;
            System.out.println("Calcul du serveur : " + nb + " * 5 = " + x);
            dos.writeInt(x);
            System.out.println("Le résultat a été envoyé au client " + x);
            nb = dis.readInt();
            System.out.println("Le serveur a reçu le nombre " + nb);
        } ;

        dis.close();
        dos.close();

        serverSocket.close();
        socket.close();
    }

    private static class accept extends Socket {
    }
}