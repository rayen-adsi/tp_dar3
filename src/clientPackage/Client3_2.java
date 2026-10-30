package ClientPackage;

import commun.Operation;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client3_2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String ipServeur = "localhost";

        InetAddress adresse = InetAddress.getByName(ipServeur);
        InetSocketAddress socketAddress = new InetSocketAddress(adresse, 1234);

        Socket socket = new Socket();
        socket.connect(socketAddress);

        System.out.println("Connecté au serveur (" + ipServeur + ").");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        int numero = ois.readInt();
        System.out.println("Je suis le client n°" + numero + ".\n");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez une opération . 0 pour quitter.");

        while (true) {
            System.out.print("Opération > ");
            String ligne = scanner.nextLine().trim();

            if (ligne.equals("0")) {
                oos.writeObject(new Operation(0, ' ', 0));
                oos.flush();
                break;
            }

            String sansEspaces = ligne.replaceAll("\\s+", "");
            if (!sansEspaces.matches(".*[+\\-*/].*")) {
                System.out.println("Erreur : aucun opérateur.");
                continue;
            }

            String[] parties = sansEspaces.split("(?=[+\\-*/])|(?<=[+\\-*/])");

            if (parties.length != 3) {
                System.out.println("Erreur : format invalide .");
                continue;
            }

            int op1, op2;
            char operateur;

            try {
                op1 = Integer.parseInt(parties[0]);
                op2 = Integer.parseInt(parties[2]);
                operateur = parties[1].charAt(0);

                if ("+-*/".indexOf(operateur) == -1) throw new Exception();
                if (operateur == '/' && op2 == 0) {
                    System.out.println("Erreur : division par zéro.");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Erreur de syntaxe. Utilisez : entier opérateur entier.");
                continue;
            }

            Operation op = new Operation(op1, operateur, op2);
            oos.writeObject(op);
            oos.flush();

            int resultat = (Integer) ois.readObject();
            System.out.println(">>> Résultat = " + resultat + "\n");
        }

        ois.close();
        oos.close();
        socket.close();
        System.out.println("Déconnecté.");
    }
}