package WichtelGenerator.Commands;

import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;
import java.util.Comparator;

public class Status extends Command {

    public Status() {
        key = "status";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        ArrayList<User> participants = Randomizer.getInstance().getParticipants();
        participants.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (participants.isEmpty()) {
            System.out.println("No participants.");
            System.out.println("Use 'add -name' to add participants");
            return;
        }
        System.out.println("There are "+ participants.size() +" enlisted participants: ");
        for (User participant : participants) {
            if (participant.getVictim() != null && args.length > 0 && args[0].equals("victims"))
                System.out.println(participant.getName() + "s' victim is " + participant.getVictim().getName());
            else
                System.out.println(participant.getName());
        }
    }

    @Override
    public String getDescription() {
        return "Lists all participants";
    }

    @Override
    public String getUsage() {
        return "status " +
                "\n use -victims if you want to see their victims too";
    }
}
