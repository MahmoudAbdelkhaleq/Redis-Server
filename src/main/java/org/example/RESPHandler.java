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
        switch (length){
            case 1:
                if (!oneWordCommands.contains(commandName)) {
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            case 2:
                if(!Arrays.asList("GET", "DEL", "EXISTS", "TYPE", "INCR", "DECR").contains(commandName)){
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            case 3:
                if(!commandName.equals("SET") && !commandName.equals("LPUSH") && !commandName.equals("RPUSH")){
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            default:
                if(!commandName.equals("LPUSH") && !commandName.equals("RPUSH")){
                    throw new IllegalArgumentException("Invalid command");
                }
        }
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
            return "ERR - empty command";
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
            return "ERR - undefined command";
        }
        String commandName = commandParts[0].toUpperCase();
        switch(commandName) {
            case "SET":
                if(commandParts.length != 3) {
                    return "ERR - wrong number of arguments for 'SET' command";
                }
                command = new SETCommand(commandParts[1], commandParts[2], db);
                break;
            case "GET":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'GET' command";
                }
                command = new GETCommand(commandParts[1], db);
                break;
            case "DEL":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'DEL' command";
                }
                command = new GETCommand(commandParts[1], db);
                break;
            case "EXISTS":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'EXISTS' command";
                }
                command = new EXISTSCommand(commandParts[1], db);
                break;
            case "INCR":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'INCR' command";
                }
                command = new INCRCommand(commandParts[1], db);
                break;
            case "DECR":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'DECR' command";
                }
                command = new DECRCommand(commandParts[1], db);
                break;
            case "LPUSH":
                if(commandParts.length < 3) {
                    return "ERR - wrong number of arguments for 'LPUSH' command";
                }
                String [] values = Arrays.copyOfRange(commandParts, 2, commandParts.length);
//                command = new LPUSHCommand(command[1], values, db);
                break;
            case "RPUSH":
                if(commandParts.length < 3) {
                    return "ERR - wrong number of arguments for 'RPUSH' command";
                }
                String [] values1 = Arrays.copyOfRange(commandParts, 2, commandParts.length);
//                command = new RPUSHCommand(command[1], values1, db);
                break;
            case "TYPE":
                if(commandParts.length != 2) {
                    return "ERR - wrong number of arguments for 'TYPE' command";
                }
                command = new TYPECommand(commandParts[1], db);
                break;
        }
        if(command != null) {
            return command.execute();
        }
        return "ERR - undefined command";
    }

}
