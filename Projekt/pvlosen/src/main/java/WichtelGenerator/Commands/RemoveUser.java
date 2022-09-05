package WichtelGenerator.Commands;

import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;
import java.util.Scanner;

public class RemoveUser extends Command {

    public RemoveUser() {
        key = "removeUser";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        ArrayList<User> results = Randomizer.getInstance().findParticipant(args[0]);
        if (results.size() == 0) {
            System.err.println("No such participant found.");
            System.err.println("Type in 'status' to get a list of all participants.");
        }
        else {
            System.out.println("Found " + results.size() + " results :");
            for (int i = 0; i < results.size(); i++) {
                System.out.println(i + ".: \t" + results.get(i));
            }
            System.out.println(" Type in the number of the participant you would like to remove \n" +
                    " or something else to cancel.");
            Scanner sc = new Scanner(System.in);
            String line = sc.nextLine();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= results.size()) {
                    System.err.println("Canceling..");
                }
                else {
                    Randomizer.getInstance().removeParticipant(results.get(choice));
                    System.out.println("Removed " + results.get(choice));
                }
            }
            catch (NumberFormatException e) {
                System.err.println("Canceling..");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Removes a user from the participants.";
    }

    @Override
    public String getUsage() {
        return "removeUser -name";
    }
}
