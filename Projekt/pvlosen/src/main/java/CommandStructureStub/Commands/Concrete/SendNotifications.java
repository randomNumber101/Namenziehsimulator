package CommandStructureStub.Commands.Concrete;

import CommandStructureStub.Commands.Command;
import CommandStructureStub.Commands.CommandParser;
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
                System.out.println("Sent mail to " + u.getName() + "(Real)");
                MailCreator.getInstance().sendNotification(u);
            }
            PermutationSaver.getInstance().saveState(participants);
        }
        User dummy = new User("dummy", "dummy@mail.com");
        User victim = new User("dummys' victim", "ligma@balls.com");
        User murderer = new User("dummys murderer", "fuck@it.com");
        dummy.setVictim(victim);
        dummy.setKiller(murderer);

        System.out.println("\n The following mail was sent: \n");
        System.out.println(MailCreator.getInstance().getMessage(dummy));
    }

    @Override
    public String getDescription() {
        return "Sends mail notifications to all participants.";
    }

    @Override
    public String getUsage() {
        return "sendNotifications";
    }
}
