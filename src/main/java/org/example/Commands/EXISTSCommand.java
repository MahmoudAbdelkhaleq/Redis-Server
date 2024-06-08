package org.example.Commands;

import org.example.DB.Database;

public class EXISTSCommand implements Command{
    String key;
    Database db;
    public EXISTSCommand(String key, Database db) {
        this.key = key;
        this.db = db;
    }
    public String execute() {
        if(db.exists(key)) {
            return "(integer) 1";
        }
        return "(integer) 0";
    }
}
