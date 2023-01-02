package com.company;

import java.io.*;
import java.math.BigInteger;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        // Create a socket to connect to the server
        Socket socket = new Socket("localhost", 8001);
        TripleDES cipher = new TripleDES();

        System.out.println("Server is connected" );

        // Create data input and output streams
        DataInputStream inputFromServer = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToServer = new DataOutputStream(socket.getOutputStream());


        BigInteger p = new BigInteger(inputFromServer.readUTF());
        System.out.println("P received from the server is " + p);
        BigInteger q = new BigInteger(inputFromServer.readUTF());
        System.out.println("Q received from the server is " + q);
        BigInteger e = new BigInteger(inputFromServer.readUTF());
        System.out.println("E received from the server is " + e);
        String cipher1 = inputFromServer.readUTF();
        System.out.println("cipher1 received from the server is " + cipher1);
        String cipher2 = inputFromServer.readUTF();
        System.out.println("cipher2 received from the server is " + cipher2);
        String cipher3 = inputFromServer.readUTF();
        System.out.println("cipher3 received from the server is " + cipher3);

        BigInteger cipher1BI = new BigInteger(cipher1);
        BigInteger cipher2BI = new BigInteger(cipher2);
        BigInteger cipher3BI = new BigInteger(cipher3);
        BigInteger decryptedMsg1 = Rsa.decrypt(p, q, e, cipher1BI);
        BigInteger decryptedMsg2 = Rsa.decrypt(p, q, e, cipher2BI);
        BigInteger decryptedMsg3 = Rsa.decrypt(p, q, e, cipher3BI);
        System.out.println("Decrypted message = " + decryptedMsg1);
        System.out.println("Decrypted message = " + decryptedMsg2);
        System.out.println("Decrypted message = " + decryptedMsg3);

        String tripleDesCipher = inputFromServer.readUTF();
        System.out.println("Triple DES Cipher received from the server is " + tripleDesCipher);

        String DDes1 = cipher.decrypt(tripleDesCipher, decryptedMsg3.toString());
        String DDes2 = cipher.encrypt(DDes1, decryptedMsg2.toString());
        String DDes3 = cipher.decrypt(DDes2, decryptedMsg1.toString());
        System.out.println("Decrypted Triple DES: " + DDes3);

    }
}