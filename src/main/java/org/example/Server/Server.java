package org.example.Server;

import org.example.RESPHandler;

import java.io.*;
import java.net.*;

public class Server {
    final static int SERVER_PORT = 6379;
    final RESPHandler RESPHandler = new RESPHandler();
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
                while (true) {
                    System.out.println("Waiting for client input...");
                    StringBuilder request = new StringBuilder();
                    request.append(in.readLine() + "\r\n");
                    if(request.equals("exit")) {
                        break;
                    }
                    while (!(clientInput = in.readLine()).isEmpty()) {
                        request.append(clientInput+ "\r\n");
                    }
//                    request = new StringBuilder(request.substring(0, request.length()-2));
                    System.out.println("Received from client: " + request);
                    String response = RESPHandler.handleRequest(request.toString());
                    System.out.println("response: " + response);
                    // Respond to the client
                    out.println(RESPHandler.serialize(response));
                }
                // Close the connection with the client
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

