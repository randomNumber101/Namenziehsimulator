package WichtelGenerator.Commands;

import WichtelGenerator.MailCreator.MailCreator;
import WichtelGenerator.fileIO.PermutationSaver;
import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;

public class SendNotifications extends Command {

    public SendNotifications() {
        key = "sendNotifications";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        ArrayList<User> participants = Randomizer.getInstance().getParticipants();
        for (User u : participants) {
            if (args.length == 0) {
                System.out.println("Sent mail to " + u.getName() + "(Test)");
            }
            else if (args[0].equals("r")) {
                System.out.println("Sending mail to " + u.getName() + "(Real)...");
                MailCreator.getInstance().sendNotification(u);
            }
            else {
                System.err.println("Argument \"" + args[0] + "\" not mapped.");
                return;
            }
            PermutationSaver.getInstance().saveState(participants);
        }
        User dummy = new User("dummy", "dummy@mail.com");
        User victim = new User("dummys' victim", "ligma@balls.com");
        dummy.setVictim(victim);
        System.out.println("\n The following mail was sent: \n");
        System.out.println(MailCreator.getInstance().getMessage(dummy));
    }

    @Override
    public String getDescription() {
        return "Sends (test) mail notifications to all participants.";
    }

    @Override
    public String getUsage() {
        return "sendNotifications \n"
                + "use -r to send them for real";
    }
}
