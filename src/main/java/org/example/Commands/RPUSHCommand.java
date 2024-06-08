package org.example.Commands;

import org.example.DB.Database;

import java.util.List;

public class RPUSHCommand implements Command{
    String key;
    Database db;
    List<String> values;
    public RPUSHCommand(String key, List<String> values, Database db) {
        this.key = key;
        this.db = db;
        this.values = values;
    }
    @Override
    public String execute() {
        db.insertValuesAtTail(key, values);
        return "(integer) " + ((List)(db.get(key))).size();
    }
}
