package org.example.Server;

import org.example.RESPHandler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final RESPHandler RESPHandler;

    public ClientHandler(Socket clientSocket, BufferedReader in, PrintWriter out, RESPHandler RESPHandler) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.RESPHandler = RESPHandler;
    }

    public void run() {
        try {
            // Read and process the client input
            String clientInput;
            while (true) {
                System.out.println("Waiting for client input...");
                StringBuilder request = new StringBuilder();
                request.append(in.readLine() + "\r\n");
                if(request.toString().equalsIgnoreCase("exit")) {
                    break;
                }
                while (!(clientInput = in.readLine()).isEmpty()) {
                    request.append(clientInput+ "\r\n");
                }
                System.out.println("Received from client: " + request);
                try{
                    String response = RESPHandler.handleRequest(request.toString());
                    System.out.println("response: " + response);
                    // Respond to the client
                    String serializedResponse = RESPHandler.serialize(response);
                    System.out.println("Sending to client: " + serializedResponse);
                    out.println(serializedResponse);
                }
                catch (IllegalArgumentException e){
                    out.println(RESPHandler.serialize("-ERR " + e.getMessage()));
                }
            }
            // Close the connection with the client
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
