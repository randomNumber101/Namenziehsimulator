package CommandStructureStub.Commands.Concrete;

import CommandStructureStub.Commands.Command;
import CommandStructureStub.Commands.CommandParser;
import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import java.util.ArrayList;

public class Status extends Command {

    public Status() {
        key = "status";
        maxArgCount = 0;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        ArrayList<User> participants = Randomizer.getInstance().getParticipants();

        if (participants.isEmpty()) {
            System.out.println("No participants.");
            System.out.println("Use 'add -name' to add participants");
        }
        System.out.println("Enlisted participants: ");
        for (User participant : participants) {
            if (participant.getVictim() != null)
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
        return "status";
    }
}
