package org.example.Commands.OneWordCommand;

import org.example.Commands.Command;
import org.example.DB.Database;

public class DBSIZECommand implements Command {
    Database db;
    public DBSIZECommand(Database db) {
        this.db = db;
    }
    @Override
    public String execute() {
        return db.getDBSize()+"";
    }
}
