package ImageCodeInjector;


import ImageCodeInjector.Command.CommandParser;
import ImageCodeInjector.Command.Concrete.Quit;
import ImageCodeInjector.Command.Concrete.Status;

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
