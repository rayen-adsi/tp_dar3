package ClientPackage;

import commun.Operation;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client3_1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connecté au serveur.");

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        int numeroClient = ois.readInt();
        System.out.println("Je suis le client n°" + numeroClient);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tapez une opération (ex: 55 * 25). 0 pour quitter.\n");

        while (true) {
            System.out.print("Opération > ");
            String ligne = scanner.nextLine().trim();

            if (ligne.equals("0")) {
                oos.writeObject(new Operation(0, ' ', 0));
                oos.flush();
                break;
            }

            String ligneSansEspaces = ligne.replaceAll("\\s+", "");

            if (!ligneSansEspaces.matches(".*[+\\-*/].*")) {
                System.out.println("Erreur : aucun opérateur trouvé (+ - * /)");
                continue;
            }

            String[] parties = ligneSansEspaces.split("(?=[+\\-*/])|(?<=[+\\-*/])");

            if (parties.length != 3) {
                System.out.println("Erreur : format invalide (ex: 25*25 ou 25 * 25)");
                continue;
            }

            int op1;
            try {
                op1 = Integer.parseInt(parties[0]);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : " + parties[0] + " n'est pas un entier");
                continue;
            }

            if (parties[1].length() != 1 || "+-*/".indexOf(parties[1].charAt(0)) == -1) {
                System.out.println("Erreur : opérateur invalide");
                continue;
            }
            char operateur = parties[1].charAt(0);

            int op2;
            try {
                op2 = Integer.parseInt(parties[2]);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : " + parties[2] + " n'est pas un entier");
                continue;
            }

            if (operateur == '/' && op2 == 0) {
                System.out.println("Erreur : division par zéro impossible");
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
        scanner.close();
        System.out.println("Client fermé.");
    }
}