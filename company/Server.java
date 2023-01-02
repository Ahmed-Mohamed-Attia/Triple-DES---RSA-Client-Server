package com.company;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8001);
        BigInteger p = new BigInteger("49380845887151");
        BigInteger q = new BigInteger("33698185273147");
        BigInteger e = Rsa.chooseE(p, q);
        String msg1 = "1973824619738246";
        String msg2 = "8246824562147896";
        String msg3 = "1236987412369874";
        String msg4 = "0777500007775000";
        BigInteger Cipher1 = Rsa.encrypt(p, q, msg1);
        BigInteger Cipher2 = Rsa.encrypt(p, q, msg2);
        BigInteger Cipher3 = Rsa.encrypt(p, q, msg3);

        TripleDES cipher = new TripleDES();
        String Des1 = cipher.encrypt(msg4, msg1);
        String Des2 = cipher.decrypt(Des1, msg2);
        String encrypted3Des = cipher.encrypt(Des2, msg3);
        System.out.println("encrypted Triple Des: " + encrypted3Des);



        System.out.println("Server e: " + e);
        // Listen for a connection request
        Socket socket = serverSocket.accept();


//        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

        outputToClient.writeUTF(p.toString());
        outputToClient.writeUTF(q.toString());
        outputToClient.writeUTF(e.toString());
        outputToClient.writeUTF(Cipher1.toString());
        outputToClient.writeUTF(Cipher2.toString());
        outputToClient.writeUTF(Cipher3.toString());
        outputToClient.writeUTF(encrypted3Des);
        while (true) {
            // Receive message from the client
//            String message = inputFromClient.readUTF();

            // Print the message
//            System.out.println("Message received from client: " + message);

            // Send a message back to the client
//            outputToClient.writeUTF("Hello from the server!");
        }
    }
}