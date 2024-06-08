package org.example.Commands;

import org.example.DB.Database;

public class TYPECommand implements Command{
    String key;
    Database db;
    public TYPECommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        return db.type(key)+"";
    }
}
