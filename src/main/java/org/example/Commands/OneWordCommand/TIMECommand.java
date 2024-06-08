package org.example.Commands.OneWordCommand;

import org.example.Commands.Command;

public class TIMECommand implements Command {
    @Override
    public String execute() {
        return String.valueOf(System.currentTimeMillis());
    }
}
