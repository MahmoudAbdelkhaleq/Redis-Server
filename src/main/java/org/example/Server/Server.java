package org.example.Server;

import org.example.RESPRequestHandler;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    final static int SERVER_PORT = 6379;
    RESPRequestHandler RESPrequestHandler = new RESPRequestHandler();
    public void start(){
        try {
            // Create a ServerSocket to listen on the specified port
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server started and listening on port " + SERVER_PORT);

            // Run indefinitely to accept multiple client connections
            while (true) {
                // Accept an incoming connection from a client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create input and output streams for communication
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read and process the client input
                String clientInput;
                while ((clientInput = in.readLine())!=null) {
                    System.out.println("Received from client: " + clientInput);
                    String output = (String)RESPrequestHandler.deserialize(clientInput);
                    // Respond to the client
                    out.println("Echo: " + output);
                }
                // Close the connection with the client
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

