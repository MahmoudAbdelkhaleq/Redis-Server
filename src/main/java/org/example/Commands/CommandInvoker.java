package org.example.Commands;

public class CommandInvoker {
    private Command command;
    public void setCommand(Command command) {
        this.command = command;
    }
    public String executeCommand() {
        return command.execute();
    }
}
