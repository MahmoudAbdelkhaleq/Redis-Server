package org.example.Serialize;

import java.util.Arrays;
import java.util.List;

public class Serializer {
    List<String> oneWordCommands = Arrays.asList("OK", "ERROR", "PING", "QUIT", "FLUSHDB", "SAVE", "DBSIZE", "FLUSHALL",
            "TIME", "INFO");
    public String serialize(String command) {
        if(command == null || command.isEmpty()) {
            return "$-1\r\n";
        }
        // only server sends simple strings and short messages
        String serializedCommand = "";
        String [] commandArray = command.split(" ");
        // handle one word commands
        if(commandArray.length == 1) {
            if(!oneWordCommands.contains(commandArray[0].toUpperCase())) {
                throw new IllegalArgumentException("Invalid command");
            }
            return "+" + commandArray[0] + "\r\n";
        }
        // If the command is an array,
        serializedCommand = "*" + commandArray.length + "\r\n";
        for(String s : commandArray) {
            serializedCommand += "$" + s.length() + "\r\n" + s + "\r\n";
        }
        // If the command is an integer,
        return serializedCommand;
    }
}
