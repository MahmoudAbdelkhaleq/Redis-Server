package org.example.Serialize;

import java.util.Arrays;
import java.util.List;

public class Deserializer {
    List<String> oneWordResponses = Arrays.asList("OK", "PONG", "QUEUED", "ERR", "BUSY", "WRONGTYPE");
    public Object deserialize(String response){
        if(response == "$-1\r\n") {
            return null;
        }
        String value = response.substring(1, response.length() - 2);
        //simple string
        if(oneWordResponses.contains(value.toUpperCase())) {
            return "OK";
        }
        //integer
        if(response.charAt(0) == ':') {
            return Integer.parseInt(value);
        }
        //error
        if(response.charAt(0) == '-') {
            return new Error(value);
        }
        //bulk string
        if(response.charAt(0) == '$') {
            int length = Integer.parseInt(response.substring(1, response.indexOf("\r\n")));
            return response.substring(response.indexOf("\r\n") + 2, response.indexOf("\r\n") + 2 + length);
        }
        //array
        if(response.charAt(0) == '*') {
            String [] responseArray = response.split("\r\n");
            String deserializedResponse = "";
            for(int i = 1; i < responseArray.length; i += 2) {
                deserializedResponse += responseArray[i + 1] + " ";
            }
            return deserializedResponse.trim();
        }
        return response;
    }
}
