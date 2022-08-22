package WichtelGenerator.fileIO;

import WichtelGenerator.randomizer.User;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PermutationSaver {
    private static PermutationSaver mInstance;
    private String fileSaveDir = "C:\\Users\\MKhal\\Desktop\\Freizeit\\PV\\Namenziehsimulator";


    private PermutationSaver() {
    }

    public static PermutationSaver getInstance() {
        if (mInstance == null) {
            mInstance = new PermutationSaver();
        }
        return mInstance;
    }

    public void saveState(ArrayList<User> users) {
        try {
            String date =   new SimpleDateFormat("yy-MM-dd_HH-mm").format(Calendar.getInstance().getTime());
            FileWriter writer = new FileWriter(new File(fileSaveDir, "permutation_" + date + ".txt" ));
            BufferedWriter bw = new BufferedWriter(writer);

            for (User u : users) {
                bw.write(u.getName() + "s' victim is " + u.getVictim().getName());
                bw.newLine();
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadInformation() {

        File f = new File (SettingLoader.getInstance().getPermutSaverDir());
        if (f.exists() && f.isDirectory())
            fileSaveDir = SettingLoader.getInstance().getPermutSaverDir();
    }
}
