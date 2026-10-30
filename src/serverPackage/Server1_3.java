package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1_3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Serveur en attente d'un client...");

        Socket socket = serverSocket.accept();
        System.out.println("Un client est connecté : " + socket.getInetAddress().getHostAddress());

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        int code;
        do {
            // 1) Lire le code de l'opération
            code = dis.readInt();
            System.out.println("Code reçu : " + code);

            // Si code == 0 → fin
            if (code == 0) {
                System.out.println("Le client a demandé la fin.");
                break;
            }

            // 2) Lire la valeur x
            int x = dis.readInt();
            System.out.println("Valeur x reçue : " + x);

            // 3) Effectuer le calcul selon le code
            int resultat = 0;
            switch (code) {
                case 1: // Addition
                    resultat = x + 5;
                    System.out.println("Addition : " + x + " + 5 = " + resultat);
                    break;
                case 2: // Soustraction
                    resultat = x - 5;
                    System.out.println("Soustraction : " + x + " - 5 = " + resultat);
                    break;
                case 3: // Multiplication
                    resultat = x * 5;
                    System.out.println("Multiplication : " + x + " * 5 = " + resultat);
                    break;
                case 4: // Division
                    if (x == 0) {
                        // On renvoie une valeur spéciale ou on gère à part
                        resultat = Integer.MIN_VALUE; // signal d'erreur
                        System.out.println("Division par 0 refusée !");
                    } else {
                        resultat = x / 5;
                        System.out.println("Division : " + x + " / 5 = " + resultat);
                    }
                    break;
                default:
                    System.out.println("Code invalide : " + code);
                    resultat = Integer.MIN_VALUE;
            }

            // 4) Envoyer le résultat
            dos.writeInt(resultat);
            dos.flush();

        } while (code != 0);

        // Fermeture
        dis.close();
        dos.close();
        socket.close();
        serverSocket.close();
        System.out.println("Serveur arrêté.");
    }
}