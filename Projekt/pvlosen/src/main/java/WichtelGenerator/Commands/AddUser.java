package WichtelGenerator.Commands;

import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;
import java.util.Scanner;

public class AddUser extends Command{

    public AddUser() {
        key = "add";
        maxArgCount = 1;
        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        if (args.length != 1) {
            CommandParser.getInstance().printWrongArguments(this, args);
            return;
        }
        ArrayList<User> results = Randomizer.getInstance().findUser(args[0]);
        if (results.size() == 0) {
            System.err.println("No such user found.");
            System.err.println("Type in 'listUsers' to get a list of all users.");
        }
        else if (results.size() == 1) {
            Randomizer.getInstance().addParticipant(results.get(0));
            System.out.println("Added " + results.get(0));
        }
        else {
            System.out.println("Found " + results.size() + " results :");
            for (int i = 0; i < results.size(); i++) {
                System.out.println(i + ".: \t" + results.get(i));
            }
            System.out.println(" Type in the number of the user you would like to add \n" +
                    " or something else to cancel.");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= results.size()) {
                    System.err.println("Canceling..");
                }
                else {
                    Randomizer.getInstance().addParticipant(results.get(choice));
                    System.out.println("Added " + results.get(choice));
                }
            }
            catch (NumberFormatException e) {
                System.err.println("Canceling..");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Adds the user to the participents.";
    }

    @Override
    public String getUsage() {
        return "add -name";
    }


}
