package org.example.Server;

import org.example.RESPRequestHandler;

import java.io.*;
import java.net.*;

public class SimpleClient {
    private RESPRequestHandler requestHandler;
    final static int PORT = 6379;
    public SimpleClient() {
        requestHandler = new RESPRequestHandler();
    }
    public void start(){
        String serverAddress = "localhost";
        try (Socket socket = new Socket(serverAddress, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server at " + serverAddress + ":" + PORT);
            String input;
            StringBuilder command = new StringBuilder();
            while ((input = userInput.readLine()) != null) {
                command.append(input);
                System.out.println("Server response: " + in.readLine());
            }
            //serialize the command and send it to the server
            out.println(requestHandler.serialize(command.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

