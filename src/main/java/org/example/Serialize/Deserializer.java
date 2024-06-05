package org.example.Serialize;

import java.util.Arrays;
import java.util.List;

public class Deserializer {
    List<String> oneWordResponses = Arrays.asList("OK", "PONG", "QUEUED", "ERR", "BUSY", "WRONGTYPE","(nil)");
    List<String> oneWordRequests = Arrays.asList("PING", "QUIT", "FLUSHDB", "SAVE", "DBSIZE", "FLUSHALL", "TIME", "INFO");
    public Object deserialize(String response){
        if(response.equals("$-1\r\n")) {
            return null;
        }
        String value = response.substring(1, response.length()-2);
        //simple string
        switch (response.charAt(0)){
            case '+':
                if(oneWordResponses.contains(value.toUpperCase()) || oneWordRequests.contains(value.toUpperCase())) {
                    return value;
                }
                else{
                    throw new IllegalArgumentException("Invalid command");
                }
            //INTEGER
            case ':':
                return Integer.parseInt(value);
            //ERROR
            case '-':
                return new Error(value);
            //bulk string
            case '$':
                int length = Integer.parseInt(response.substring(1, response.indexOf("\r\n")));
                return response.substring(response.indexOf("\r\n") + 2, response.indexOf("\r\n") + 2 + length);
            //array
            case '*':
                String [] responseArray = response.split("\r\n");
                String [] deserializedResponse = new String [Integer.parseInt(responseArray[0].substring(1))];
                int counter = 0;
                for(int i = 2; i < responseArray.length; i += 2) {
                    deserializedResponse[counter++] = responseArray[i];
                }
                return deserializedResponse;
            default:
                throw new IllegalArgumentException("Invalid command");
        }
    }

    //test
    public static void main(String[] args) {
        Deserializer deserializer = new Deserializer();
        System.out.println(deserializer.deserialize("+OK\r\n"));
        System.out.println(deserializer.deserialize(":1\r\n"));
        System.out.println(deserializer.deserialize("$6\r\nfoobar\r\n"));
        System.out.println(deserializer.deserialize("$-1\r\n"));
        System.out.println(deserializer.deserialize("*2\r\n$3\r\nfoo\r\n$3\r\nbar\r\n"));
        System.out.println(deserializer.deserialize("-ERR unknown command 'foobar'\r\n"));
    }
}
