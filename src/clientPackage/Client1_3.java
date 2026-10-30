package clientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1_3 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Client connecté au serveur.");

        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);

        int code;
        do {
            // 1) Afficher le menu
            System.out.println("\n===== MENU CALCULATRICE =====");
            System.out.println("1. Addition        (x + 5)");
            System.out.println("2. Soustraction    (x - 5)");
            System.out.println("3. Multiplication  (x * 5)");
            System.out.println("4. Division        (x / 5)");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            code = scanner.nextInt();

            // 2) Envoyer le code
            dos.writeInt(code);
            dos.flush();

            // Si 0 → on sort avant de demander x
            if (code == 0) {
                System.out.println("Fin de la communication.");
                break;
            }

            // Vérifier que le code est valide
            if (code < 1 || code > 4) {
                System.out.println("Choix invalide !");
                continue;
            }

            // 3) Demander x
            System.out.print("Entrez la valeur de x : ");
            int x = scanner.nextInt();

            // Envoyer x
            dos.writeInt(x);
            dos.flush();

            // 4) Lire le résultat
            int resultat = dis.readInt();

            // 5) Afficher le résultat
            if (resultat == Integer.MIN_VALUE) {
                System.out.println("Erreur de calcul (division par zéro ?)");
            } else {
                System.out.println(">>> Résultat = " + resultat);
            }

        } while (code != 0);

        // Fermeture
        dis.close();
        dos.close();
        socket.close();
        scanner.close();
    }
}