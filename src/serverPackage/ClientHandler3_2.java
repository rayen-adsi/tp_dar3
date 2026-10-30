package ServerPackage;

import commun.Operation;
import java.io.*;
import java.net.Socket;

public class ClientHandler3_2 implements Runnable {

    private Socket socket;
    private int numeroClient;

    private static int totalOperations = 0;

    public ClientHandler3_2(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    public void run() {
        try {
            String ip = socket.getRemoteSocketAddress().toString();
            System.out.println("Client n°" + numeroClient + " connecté (" + ip + ") : ");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeInt(numeroClient);
            oos.flush();

            while (true) {
                Operation op = (Operation) ois.readObject();

                if (op.getOp1() == 0) {
                    System.out.println("Client n°" + numeroClient + " a quitté.");
                    break;
                }

                int resultat = 0;
                switch (op.getOperateur()) {
                    case '+': resultat = op.getOp1() + op.getOp2(); break;
                    case '-': resultat = op.getOp1() - op.getOp2(); break;
                    case '*': resultat = op.getOp1() * op.getOp2(); break;
                    case '/': resultat = op.getOp1() / op.getOp2(); break;
                }

                synchronized (ClientHandler3_2.class) {
                    totalOperations++;
                    System.out.println("[Client n°" + numeroClient + "] " + op + " = " + resultat
                            + " | Total ops = " + totalOperations);
                }
                oos.writeObject(resultat);
                oos.flush();
            }

            ois.close();
            oos.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Erreur client n°" + numeroClient + " : " + e.getMessage());
        }
    }
}