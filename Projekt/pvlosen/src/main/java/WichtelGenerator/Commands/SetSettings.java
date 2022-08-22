package WichtelGenerator.Commands;

import WichtelGenerator.fileIO.SettingLoader;

public class SetSettings extends Command {


    public SetSettings() {
        key = "setSettings";
        maxArgCount = 1;

        CommandParser.getInstance().registerCommand(this);
    }

    @Override
    public void run(String[] args) {
        if (args == null || args.length < 1) {
            System.err.println("No arguments found. Use 'help -setSettings' for more information.");
            return;
        }
        SettingLoader.getInstance().loadSettings(args[0]);
        System.out.println("Settings loaded!");
    }

    @Override
    public String getDescription() {
        return "Sets the settings file.";
    }

    @Override
    public String getUsage() {
        return "setSettings -path_to_settings_file";
    }
}
