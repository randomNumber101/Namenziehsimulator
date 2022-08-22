package ImageCodeInjector.Command.Concrete;

import ImageCodeInjector.Command.Command;
import ImageCodeInjector.Command.CommandParser;

import java.util.Collection;
import java.util.HashMap;

public class Help extends Command {

    public Help() {
        key = "help";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        HashMap<String, Command> commandHashMap = CommandParser.getInstance().getCommands();
        if (args.length == 0) {
            Collection<Command> commands = commandHashMap.values();
            System.out.println("Type in 'command -arg -arg -...");
            for (Command c : commands) {
                System.out.println(c.key);
            }
            System.out.println("Type in 'help -command' to get a description and usage of the command.");
        }
        else {
            if (!commandHashMap.containsKey(args[0])) {
                System.err.println("Command '" + args[0] + "' could not be found.");
                System.err.println("Type in 'help' to get a list of valid commands.");
            }
            else {
                Command c = commandHashMap.get(args[0]);
                System.out.println("Usage: \n" + c.getUsage());
                System.out.println("Description:  \n" + c.getDescription());
            }
        }

    }

    @Override
    public String getDescription() {
        return "Lists all commands or gives specific help for one command.";
    }

    @Override
    public String getUsage() {
        return "help or help -command_key";
    }
}
