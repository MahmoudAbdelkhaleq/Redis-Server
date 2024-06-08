package org.example.Server;

import org.example.RESPHandler;

import java.io.*;
import java.net.*;

public class Client {
    final private RESPHandler RESPHandler;
    final static int PORT = 6379;
    public Client() {
        RESPHandler = new RESPHandler();
    }
    public void start(){
        String serverAddress = "localhost";
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(serverAddress, PORT);
            System.out.println("Connected to the server at " + serverAddress + ":" + PORT);

            // Create input and output streams for communication
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));


            // Read user input and send it to the server
            System.out.print("redis-cli> ");
            String input;
            while ((input = userInput.readLine())!=null) {
                try {
                    RESPHandler.verifyRequest(input);
                    String serializedRequest = RESPHandler.serialize(input);
                    System.out.println("Sending to server serialized command: " + serializedRequest);
                    out.println(serializedRequest);
                    StringBuilder serverResponse = new StringBuilder();
                    String serverInput;
                    while (!(serverInput = in.readLine()).isEmpty()) {
                        serverResponse.append(serverInput + "\r\n");
                    }
                    System.out.println("RESP Response: " + serverResponse);
                    System.out.println("Server response: " + RESPHandler.deserialize(serverResponse.toString()));
                    System.out.print("redis-cli> ");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.print("redis-cli> ");
                }
            }

            // Close the connection
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}

