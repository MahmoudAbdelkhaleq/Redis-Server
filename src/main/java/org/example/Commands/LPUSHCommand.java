package org.example.Commands;

import org.example.DB.Database;

import java.util.List;

public class LPUSHCommand implements Command{
    String key;
    Database db;
    List<String> values;
    public LPUSHCommand(String key, List<String> values, Database db) {
        this.key = key;
        this.db = db;
        this.values = values;
    }
    @Override
    public String execute() {
        return db.insertValuesAtHead(key, values);
//        return "(integer) " + (List)(db.get(key)).size();
    }
}
