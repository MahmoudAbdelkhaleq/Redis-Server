package org.example;

import org.example.DB.Database;
import org.example.Serialize.Deserializer;
import org.example.Serialize.Serializer;

public class RESPRequestHandler {
    private Deserializer deserializer;
    private Serializer serializer;
    private Database db;
    public RESPRequestHandler() {
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
}
