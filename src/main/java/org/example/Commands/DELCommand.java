package org.example.Commands;

import org.example.DB.Database;

public class DELCommand implements Command{
    String key;
    Database db;
    public DELCommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        db.delete(key);
        return "OK";
    }
}
