package WichtelGenerator.Main;

import WichtelGenerator.Commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    private static Console mInstance;
    CommandParser parser;
    boolean running;

    public static void main(String[] args) {
        Console.getInstance().startReading();
    }

    private Console() {
        running = true;
        parser = CommandParser.getInstance();
        initCommands();
    }

    private void initCommands() {
        new AddUser();
        new Help();
        new ListUsers();
        new ReadFile();
        new RemoveUser();
        new SetMailFile();
        new SetSettings();
        new SendNotifications();
        new StartRandomizer();
        new Status();
        new Quit();
    }

    public static Console getInstance() {
        if (mInstance == null) {
            mInstance = new Console();
        }
        return mInstance;
    }

    public void startReading() {

        System.out.println("Welcome! \n" +
                " This tool enables you to create a cyclic dependency graph of a list of users. \n" +
                " It takes a list of all users represented in a csv file that may be added (use 'add') to the cyclic graph. \n " +
                " You may also send notifications per e-mail if you'd like. (use 'sendNotification') \n"+
                " Use 'help' for more information on how to use this tool."
        );
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            try {
                String line = reader.readLine();
                parser.runLine(line);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
