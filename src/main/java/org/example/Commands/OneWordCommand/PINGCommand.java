package org.example.Commands.OneWordCommand;

import org.example.Commands.Command;

public class PINGCommand implements Command {
    @Override
    public String execute() {
        return "PONG";
    }
}
