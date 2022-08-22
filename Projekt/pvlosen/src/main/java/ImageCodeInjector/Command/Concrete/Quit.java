package ImageCodeInjector.Command.Concrete;

import ImageCodeInjector.Command.Command;
import ImageCodeInjector.Command.CommandParser;
import WichtelGenerator.Main.Console;

public class Quit extends Command {

    public Quit() {
        key = "quit";
        maxArgCount = 0;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        Console.getInstance().setRunning(false);
    }

    @Override
    public String getDescription() {
        return "Quits the program.";
    }

    @Override
    public String getUsage() {
        return "quit";
    }
}
