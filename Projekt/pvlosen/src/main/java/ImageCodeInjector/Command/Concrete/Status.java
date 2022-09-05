package ImageCodeInjector.Command.Concrete;

import ImageCodeInjector.Command.Command;
import ImageCodeInjector.Command.CommandParser;

public class Status extends Command {

    public Status() {
        key = "status";
        maxArgCount = 0;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {

    }

    @Override
    public String getDescription() {
        return "Tells the current status.";
    }

    @Override
    public String getUsage() {
        return "status";
    }
}
