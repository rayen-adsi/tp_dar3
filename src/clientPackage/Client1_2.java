package clientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client1_2 {
    public static void main(String[] args) throws IOException {
        System.out.println("Je suis un client pas encore connecté…");

        Socket socket = new Socket("localhost", 1234);
        System.out.println("je suis un client connecté");
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os) ;
        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        Scanner scanner = new Scanner (System. in);
        System.out.println("Enter un entier x : ");
        int x = scanner.nextInt();
        dos.writeInt(x);
        while (x!=0) {
            System.out.println("Nombre envoyé au serveur :" + x);
            int xx = dis.readInt();
            System.out.println("Resultat recu du server: " + xx);
            System.out.println("Enter un entier x : ");
            x = scanner.nextInt();
            dos.writeInt(x);
        }

        dis.close();
        dos.close();

        socket.close();
    }
}