package CommandStructureStub.Commands.Concrete;

import CommandStructureStub.Commands.Command;
import CommandStructureStub.Commands.CommandParser;
import WichtelGenerator.fileIO.UserListReader;

public class ReadFile extends Command {

    public ReadFile() {
        key = "readFile";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        UserListReader.getInstance().setReadFile(args[0]);
    }

    @Override
    public String getDescription() {
        return "Sets the given file for the user data. \n" +
                "File should be csv. First entry per line should be name. Second entry should be email. \n" +
                "Other entries will be ignored.";
    }

    @Override
    public String getUsage() {
        return "readFile -path_to_csv_file";
    }
}
