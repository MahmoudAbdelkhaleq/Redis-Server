package org.example.Commands;

import org.example.DB.Database;

public class SETCommand implements Command{
    String key;
    String value;
    Database db;
    public SETCommand(String key, String value, Database db) {
        this.key = key;
        this.value = value;
        this.db = db;
    }
    public String execute() {
        db.set(key, value);
        return "OK";
    }
}
