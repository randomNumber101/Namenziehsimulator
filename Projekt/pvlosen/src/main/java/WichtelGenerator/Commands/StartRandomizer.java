package WichtelGenerator.Commands;

import WichtelGenerator.randomizer.Randomizer;

public class StartRandomizer extends Command {

    public StartRandomizer() {
        key = "shuffle";
        maxArgCount = 0;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        Randomizer.getInstance().randomize();
        System.out.println("Victims shuffled. Type 'status' to see new order.");
    }

    @Override
    public String getDescription() {
        return "Shuffles the victims of the participants.";
    }

    @Override
    public String getUsage() {
        return "shuffle";
    }
}
