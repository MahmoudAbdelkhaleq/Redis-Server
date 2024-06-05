package org.example;

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
    final static List<String> oneWordCommands = Arrays.asList("PING", "QUIT", "FLUSHDB", "SAVE", "DBSIZE", "FLUSHALL",
            "TIME", "INFO");
    public RESPHandler() {
        deserializer = new Deserializer();
        serializer = new Serializer();
        db = new Database();
    }
    public String serialize(String request) {
        return serializer.serialize(request);
    }
    public Object deserialize(String response) {
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
        switch (length){
            case 1:
                if (!oneWordCommands.contains(command[0].toUpperCase())) {
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            case 2:
                if(!Arrays.asList("GET", "DEL", "EXISTS", "TYPE", "INCR", "DECR").contains(command[0])){
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            case 3:
                if(!command[0].equals("SET")){
                    throw new IllegalArgumentException("Invalid command");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid command");
        }
    }
    public String handleRequest(String request) {
        System.out.println("Command: " + request);
        Object command = deserialize(request);
        System.out.println("Deserialized command: " + command);
        if(command instanceof String) {
            return handleStringCommand((String) command);
        }
        if(command instanceof String[]) {
            return handleArrayCommand((String[]) command);
        }
        if(command instanceof Integer) {
            return handleIntegerCommand((Integer) command);
        }
        if(command instanceof Error) {
            return handleErrorCommand((Error) command);
        }
        return "OK";
    }
    public String handleStringCommand(String command) {
        switch(command) {
            case "Time":
                return String.valueOf(System.currentTimeMillis());
            case "INFO":
                return "Server is running on port 6379";
            case "PING":
                return "PONG";
            case "DBSIZE":
                return String.valueOf(db.getDBSize());
            case "default":
                return "OK";
        }
        return "ERR - undefined command";
    }
    public String handleIntegerCommand(Integer num) {
        return num+"";
    }
    public String handleErrorCommand(Error err){
        return err.toString();
    }
    public String handleArrayCommand(String[] command) {
        if(command.length == 0) {
            return "ERR - empty command";
        }
        String commandName = command[0];
        switch(commandName) {
            case "SET":
                if(command.length != 3) {
                    return "ERR - wrong number of arguments for 'SET' command";
                }
                db.set(command[1], command[2]);
                return "OK";
            case "GET":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'GET' command";
                }
                Object value = db.get(command[1]);
                if(value == null) {
                    return null;
                }
                return value.toString();
            case "DEL":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'DEL' command";
                }
                db.delete(command[1]);
                return "OK";
            case "EXISTS":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'EXISTS' command";
                }
                return db.exists(command[1]) ? "(integer) 1" : "(integer) 0";
            case "INCR":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'INCR' command";
                }
                return db.increment(command[1])+"";
//                return "OK";
            case "DECR":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'DECR' command";
                }
                return db.decrement(command[1])+"";
//                return "OK";
            case "LPUSH":

            case "TYPE":
                if(command.length != 2) {
                    return "ERR - wrong number of arguments for 'TYPE' command";
                }
                String type = db.getType(command[1]);
                if(type == null) {
                    return "(nil)";
                }
                return type;
//            case "default":
//                return "OK";
        }
        return "ERR - undefined command";
    }
}
