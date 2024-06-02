package org.example;

import org.example.Server.Server;
import org.example.Server.SimpleClient;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        SimpleClient client = new SimpleClient();
        client.start();
    }
}