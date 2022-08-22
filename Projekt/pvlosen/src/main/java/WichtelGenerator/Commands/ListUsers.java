package WichtelGenerator.Commands;

import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;

public class ListUsers extends Command {

    public ListUsers() {
        key = "listUsers";
        maxArgCount = 2;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        ArrayList<User> users = Randomizer.getInstance().getAllUser();
        if (users == null) {
            System.err.println("You first need to load a user list.");
            return;
        }
        System.out.println("All users: ");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Override
    public String getDescription() {
        return "Lists all users.";
    }

    @Override
    public String getUsage() {
        return "listUsers";
    }
}
