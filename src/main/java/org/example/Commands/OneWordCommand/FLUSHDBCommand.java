package org.example.Commands.OneWordCommand;

import org.example.Commands.Command;
import org.example.DB.Database;

public class FLUSHDBCommand implements Command {
    Database db;
    public FLUSHDBCommand(Database db) {
        this.db = db;
    }
    @Override
    public String execute() {
        db.flushDB();
        return "OK";
    }
}
