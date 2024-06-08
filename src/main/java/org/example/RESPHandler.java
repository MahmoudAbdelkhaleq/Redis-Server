package org.example;

import org.example.Commands.*;
import org.example.Commands.OneWordCommand.*;
import org.example.DB.Database;
import org.example.Serialize.Deserializer;
import org.example.Serialize.Serializer;

import java.util.Arrays;
import java.util.List;

public class RESPHandler extends Thread{
    final private Deserializer deserializer;
    final private Serializer serializer;
    final private Database db;
//    final static List<String> oneWordResponses = Arrays.asList("OK", "PONG", "QUEUED", "ERR", "BUSY", "WRONGTYPE","(nil)");
    final static List<String> oneWordCommands = Arrays.asList("PING", "QUIT", "FLUSHDB", "SAVE", "DBSIZE",
            "TIME", "INFO");
    public RESPHandler() {
        deserializer = new Deserializer();
        serializer = new Serializer();
        db = new Database();
    }
    public String serialize(String request) {
        return serializer.serialize(request);
    }
    public String deserialize(String response) {
        return deserializer.deserialize(response);
    }
    public void verifyRequest(String request) {
        if(request == null || request.isEmpty()) {
            throw new IllegalArgumentException("Invalid command");
        }
        String [] command = request.split(" ");
        // case of printing we don't care what comes next
        if(command[0].equals("ECHO")) {
            return;
        }
        int length = command.length;
        String commandName = command[0].toUpperCase();
        if(oneWordCommands.contains(commandName)) {
            if(length != 1)
                throw new IllegalArgumentException(commandName + " doesn't take any arguments");
            return;
        }
        if(Arrays.asList("GET", "DEL", "EXISTS", "TYPE", "INCR", "DECR").contains(commandName)){
            if(length != 2)
                throw new IllegalArgumentException(commandName + " takes only one argument");
            return;
        }
        if(commandName.equals("SET")){
            if(length != 3)
                throw new IllegalArgumentException("SET takes two arguments");
            return;
        }
        if(commandName.equals("LPUSH") || commandName.equals("RPUSH")) {
            if(length < 3)
                throw new IllegalArgumentException(commandName + " takes at least two arguments");
            return;
        }
        throw new IllegalArgumentException("Invalid command");
    }
    public String handleRequest(String request) {
        System.out.println("Command: " + request);
        String command = deserialize(request);
        System.out.println("Deserialized command: " + command);
        if(command instanceof String) {
            return handleStringCommand(command);
        }
        return "OK";
    }
    public String handleStringCommand(String request) {
        String [] commandParts = request.split(" ");
        if(commandParts.length == 0) {
            throw new IllegalArgumentException("ERR - empty command");
        }
        Command command = null;
        if(commandParts.length == 1) {
            request = request.toUpperCase();
            switch (request) {
                case "TIME":
                    command = new TIMECommand();
                    break;
                case "INFO":
                    command = new INFOCommand();
                    break;
                case "PING":
                    command = new PINGCommand();
                    break;
                case "DBSIZE":
                    command = new DBSIZECommand(db);
                    break;
                case "FLUSHDB":
                    command = new FLUSHDBCommand(db);
                    break;
                case "SAVE":
                    command = new SAVECommand();
                    break;
            }
            if(command != null) {
                return command.execute();
            }
        }
        String commandName = commandParts[0].toUpperCase();
        switch(commandName) {
            case "SET":
                command = new SETCommand(commandParts[1], commandParts[2], db);
                break;
            case "GET":
                command = new GETCommand(commandParts[1], db);
                break;
            case "DEL":
                command = new DELCommand(commandParts[1], db);
                break;
            case "EXISTS":
                command = new EXISTSCommand(commandParts[1], db);
                break;
            case "INCR":
                command = new INCRCommand(commandParts[1], db);
                break;
            case "DECR":
                command = new DECRCommand(commandParts[1], db);
                break;
            case "LPUSH":
                String [] values = Arrays.copyOfRange(commandParts, 2, commandParts.length);
                command = new LPUSHCommand(commandParts[1], Arrays.asList(values), db);
                break;
            case "RPUSH":
                values = Arrays.copyOfRange(commandParts, 2, commandParts.length);
                command = new RPUSHCommand(commandParts[1], Arrays.asList(values), db);
                break;
            case "TYPE":
                command = new TYPECommand(commandParts[1], db);
                break;
        }
        if(command != null) {
            return command.execute();
        }
        throw new IllegalArgumentException("ERR - undefined command");
    }

}
