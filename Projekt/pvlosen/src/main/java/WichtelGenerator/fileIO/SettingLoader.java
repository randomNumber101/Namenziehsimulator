package WichtelGenerator.fileIO;

import WichtelGenerator.MailCreator.MailCreator;
import WichtelGenerator.randomizer.Randomizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SettingLoader {

    private static SettingLoader mInstance;


    private String settingsAddress;
    private String mailAddress;
    private String mailPW;
    private String mailFilePath;
    private String userListPath;
    private String permutSaverDir;
    private String smtpAddress;
    private String csvFormat;

    private SettingLoader() {
    }

    public void loadSettings(String settingsFilePath) {
        try {

            File f = new File(settingsFilePath);
            if (!f.exists()) {
                System.err.println("Settings file does not exist.");
            }
            this.settingsAddress = settingsFilePath;

            System.out.println("Reading settings file...");

            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";

            int line = 0;   // The line number
            while ((readLine = b.readLine()) != null) {

                if (readLine.startsWith("//"))      // Comments start with '//'
                    continue;

                if (readLine.equals("#empty"))
                    readLine = null;
                switch (line) {
                    case 0 :
                        if (readLine != null) {
                            userListPath = readLine;
                            UserListReader.getInstance().setReadFile(userListPath);
                        }
                        break;
                    case 1 :
                        if (readLine != null) {
                            mailFilePath = readLine;
                            MailCreator.getInstance().reloadInformation();
                        }
                        break;
                    case 2 :
                        if (readLine != null) {
                            mailAddress = readLine;
                            MailCreator.getInstance().reloadInformation();
                        }
                        break;
                    case 3 :
                        if (readLine != null) {
                            mailPW = readLine;
                            MailCreator.getInstance().reloadInformation();
                        }
                        break;
                    case 4 :
                        if (readLine != null) {
                            smtpAddress = readLine;
                            MailCreator.getInstance().reloadInformation();
                        }
                        break;
                    case 5:
                        if (readLine != null) {
                            permutSaverDir = readLine;
                            MailCreator.getInstance().reloadInformation();
                        }
                        break;
                    case 6:
                        if (readLine != null) {
                            csvFormat = readLine;
                            Randomizer.getInstance().reloadUserFile();
                        }
                        break;

                    default:
                        break;

                }
                line++;
            }


            PermutationSaver.getInstance().reloadInformation();


            System.out.println("Settings file read!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadSettings() {
        loadSettings(settingsAddress);
    }

    public static SettingLoader getInstance() {
        if (mInstance == null) {
            mInstance = new SettingLoader();
        }
        return mInstance;
    }

    public String getSettingsAddress() {
        return settingsAddress;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getMailPW() {
        return mailPW;
    }

    public String getMailFilePath() {
        return mailFilePath;
    }

    public String getUserListPath() {
        return userListPath;
    }

    public String getPermutSaverDir() {
        return permutSaverDir;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public String getCsvFormat() {return csvFormat;}
}
