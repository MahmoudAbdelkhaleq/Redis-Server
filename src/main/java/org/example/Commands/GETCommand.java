package org.example.Commands;

import org.example.DB.Database;

public class GETCommand implements Command{
    String key;
    Database db;
    public GETCommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        return db.get(key) == null ? "(nil)" :db.get(key)+"";
    }
}
