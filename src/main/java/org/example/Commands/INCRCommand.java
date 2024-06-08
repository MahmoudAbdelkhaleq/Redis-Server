package org.example.Commands;

import org.example.DB.Database;

public class INCRCommand implements Command{
    String key;
    Database db;
    public INCRCommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        return db.increment(key)+"";
    }
}
