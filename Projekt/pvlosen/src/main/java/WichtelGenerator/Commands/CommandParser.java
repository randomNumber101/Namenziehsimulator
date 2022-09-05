package WichtelGenerator.Commands;

import java.util.Arrays;
import java.util.HashMap;

public class CommandParser {

    private static CommandParser instance;
    private HashMap<String, Command> commands;

    private final String argSep = " -";


    private CommandParser() {
        commands = new HashMap<>();
    }

    public Command runLine(String line) {
        String[] params;
        params = line.contains(argSep) ? line.split(argSep) : new String[]{line};
        if (commands.containsKey(params[0])) {
            Command command = commands.get(params[0]);
            String[] args = params.length > 1 ? Arrays.copyOfRange(params, 1, params.length) : new String[]{};
            if (args.length > command.maxArgCount) {
                printWrongArguments(command, args);
            }
            else {
                command.run(args);
                return command;
            }
        }
        else {
            printUnknownCommand(params[0]);
        }
        return null;
    }


    private void printFormatError() {
        System.err.println("Wrong format. Format must be : ");
        System.err.println("command -arg -arg -arg ...");
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    private void printUnknownCommand (String command) {
        System.err.println("Unknown command : " + command);
        System.err.println("Type in 'help' to get a list of valid commands.");
    }

    public void printWrongArguments(Command command, String[] args) {
        String concatedArgs = "";
        for (String s : args) {
            concatedArgs = concatedArgs.concat(s);
        }
        System.err.println("The arguments " + concatedArgs + " cannot be applied to " + command.getKey());
        System.err.println("Type in: 'help " + command.getKey() + "' for a list of valid arguments.");
    }

    public void registerCommand (Command command) {
        if (command.getKey() == null)
            throw new NullPointerException("WichtelGenerator.Commands key is null: " + command);
        commands.put(command.getKey(), command);
    }

    public static CommandParser getInstance() {
        if (instance == null) {
            instance = new CommandParser();
        }
        return instance;
    }
}
