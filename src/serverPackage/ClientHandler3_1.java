package ServerPackage;

import commun.Operation;
import java.io.*;
import java.net.Socket;

public class ClientHandler3_1 implements Runnable {

    private Socket socket;
    private int numeroClient;
    private static int compteurReponse = 0;

    public ClientHandler3_1(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    public void run() {
        try {
            String ipClient = socket.getRemoteSocketAddress().toString();
            System.out.println("Nouveau client connecté :");
            System.out.println("Client n°" + numeroClient);
            System.out.println("Adresse IP : " + ipClient);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeInt(numeroClient);
            oos.flush();

            while (true) {
                Operation op = (Operation) ois.readObject();

                if (op.getOp1() == 0) {
                    System.out.println("Client n°" + numeroClient + " a demandé la fin.");
                    break;
                }

                int op1 = op.getOp1();
                char operateur = op.getOperateur();
                int op2 = op.getOp2();
                int resultat = 0;

                switch (operateur) {
                    case '+': resultat = op1 + op2; break;
                    case '-': resultat = op1 - op2; break;
                    case '*': resultat = op1 * op2; break;
                    case '/': resultat = op1 / op2; break;
                }

                System.out.println("[Client n°" + numeroClient + "] Calcul : "
                        + op1 + " " + operateur + " " + op2 + " = " + resultat);

                oos.writeObject(resultat);
                oos.flush();
            }

            ois.close();
            oos.close();
            socket.close();
            System.out.println("Client n°" + numeroClient + " déconnecté.");

        } catch (Exception e) {
            System.out.println("Erreur avec le client n°" + numeroClient + " : " + e.getMessage());
        }
    }
}