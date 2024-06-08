package org.example.Commands;

import org.example.DB.Database;

public class DECRCommand implements Command{
    String key;
    Database db;
    public DECRCommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        return db.decrement(key)+"";
    }
}
