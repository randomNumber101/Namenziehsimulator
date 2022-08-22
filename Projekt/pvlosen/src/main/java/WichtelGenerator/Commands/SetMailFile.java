package WichtelGenerator.Commands;

import WichtelGenerator.MailCreator.MailCreator;

import java.io.File;

public class SetMailFile extends Command {

    private File mailFile = null;

    public SetMailFile() {
        key = "setMailFile";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
       if (args.length > 0 && args[0].endsWith(".txt")) {
           File file = new File(args[0]);
           if (file.exists() ) {
               mailFile = file;
               MailCreator.getInstance().setMailFile(file);
           }
       }
    }

    @Override
    public String getDescription() {
        return "Sets the mail construction file if available.";
    }

    @Override
    public String getUsage() {
        return "setMailFile -path_to_file";
    }
}
