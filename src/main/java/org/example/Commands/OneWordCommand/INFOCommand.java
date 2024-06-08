package org.example.Commands.OneWordCommand;

import org.example.Commands.Command;

public class INFOCommand implements Command {
    @Override
    public String execute() {
        return "Server is running on port 6379";
    }
}
