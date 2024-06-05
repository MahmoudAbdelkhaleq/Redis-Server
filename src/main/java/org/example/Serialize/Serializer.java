package org.example.Serialize;


public class Serializer {
    public String serialize(String command) {
        if(command == null || command.isEmpty()) {
            return "$-1\r\n";
        }
        // only server sends simple strings and short messages
        String serializedCommand = "";
        String [] commandArray = command.split(" ");
        // handle one word commands
        if(commandArray.length == 1) {
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

    //test
    public static void main(String[] args) {
        Serializer serializer = new Serializer();
        System.out.println(serializer.serialize("PING"));
        System.out.println(serializer.serialize("SET key value"));
        System.out.println(serializer.serialize("GET key"));
        System.out.println(serializer.serialize("INCR key"));
        System.out.println(serializer.serialize("DEL key"));
        System.out.println(serializer.serialize("FLUSHDB"));
        System.out.println(serializer.serialize("SAVE"));
        System.out.println(serializer.serialize("DBSIZE"));
        System.out.println(serializer.serialize("FLUSHALL"));
        System.out.println(serializer.serialize("TIME"));
        System.out.println(serializer.serialize("INFO"));
        System.out.println(serializer.serialize("1"));
    }
}
