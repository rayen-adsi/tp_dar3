package clientPackage;

import java.io.*;
import java.net.Socket;

public class Client1_1 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Client connecté au serveur.");

        socket.close();
    }
}