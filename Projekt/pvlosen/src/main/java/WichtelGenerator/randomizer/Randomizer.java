package WichtelGenerator.randomizer;

import WichtelGenerator.fileIO.UserListReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Randomizer {

    private static Randomizer mInstance;
    private ArrayList<User> participants;
    private ArrayList<User> allUser;
    private String userListString = "";

    public Randomizer() {
        participants = new ArrayList<>();
        try {
            allUser = UserListReader.getInstance().getCompleteList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Randomizer getInstance() {
        if (mInstance == null) {
            mInstance = new Randomizer();
        }
        return mInstance;
    }

    public void addParticipant(User user) {
        participants.add(user);
        refreshVictims();
    }

    public void removeParticipant(User user) {
        participants.remove(user);
        refreshVictims();
    }

    public ArrayList<User> findUser(String name) {
        return getUsers(name, allUser);
    }

    public ArrayList<User> findParticipant(String name) {
        return getUsers(name, participants);
    }

    private ArrayList<User> getUsers(String name, ArrayList<User> participants) {
        String regex = ".*" + name + ".*";
        regex = regex.toLowerCase();
        regex = regex.replace("ä", ".");
        regex = regex.replace("ö", ".");
        regex = regex.replace("ü", ".");
        return findUserRegex(regex, participants);
    }

    public ArrayList<User> findUserRegex(String regex, ArrayList<User> list) {
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        ArrayList<User> output = new ArrayList<>();
        for (User user : list) {
            String name = user.getName();
            Matcher m = p.matcher(name);
            if (m.matches())
                output.add(user);
        }
        return output;
    }

    public void randomize() {
        Collections.shuffle(participants);
        refreshVictims();
    }

    private void refreshVictims() {
        for (int i = 0; i < participants.size(); i++) {

            User participant = participants.get(i);
            User victim = participants.get((i+1) % participants.size());
            User killer = participants.get(((i-1) + participants.size()) % participants.size());

            participant.setVictim(victim);
            participant.setKiller(killer);
            killer.setVictim(participant);
            victim.setKiller(participant);
        }

        ArrayList<User> pCopy = new ArrayList<User>();
        pCopy.addAll(participants);
        Collections.sort(pCopy);
        userListString = "";
        for (User p : pCopy) {
            userListString += "\n\t - " + p.getName();
        }
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public ArrayList<User> getAllUser() {
        return allUser;
    }

    public String getUserListString() {
        return userListString;
    }

    public void reloadUserFile() {
        try {
            allUser = UserListReader.getInstance().getCompleteList();
        }

        catch (Exception e) {
            System.err.println("Couldn't load user file.");
        }
    }
}
