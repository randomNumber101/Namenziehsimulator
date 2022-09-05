package WichtelGenerator.fileIO;

import WichtelGenerator.randomizer.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class UserListReader {

    File csvFile;
    String csvSeparator = ",";


    private static UserListReader mInstance;

    private UserListReader() {
    }

    public static UserListReader getInstance() {
        if (mInstance == null) {
            mInstance = new UserListReader();
        }
        return mInstance;
    }

    public ArrayList<User> getCompleteList() throws IOException {
        if (csvFile != null && csvFile.exists()) {
            ArrayList<User> output = new ArrayList<>();

            CSVFormat format = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build();

            FileReader data = new FileReader(csvFile);
            CSVParser parser = CSVParser.parse(data, format);

            String headerFormat = SettingLoader.getInstance().getCsvFormat();


            Function<CSVRecord, User> toUserObject;
            if (headerFormat.equals("pvid")) {
                toUserObject = new PVIDFormatConsumer();
            } else if (headerFormat.equals("barsys")) {
                toUserObject = new BARSYSFormatConsumer();
            } else {
                toUserObject = new DEFAULTFormatConsumer();
            }
            try {
                for (CSVRecord record : parser) {
                    output.add(toUserObject.apply(record));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return output;
        }
        else {
            System.err.println("File is not set yet.");
            return null;
        }
    }

    public void setReadFile(String path) {
        if (path == null) {
            System.err.println("User list file path is null.");
            return;
        }
        csvFile = new File(path);
        if (!csvFile.exists()) {
            System.err.println("File at " + path + " not found.");
        }
    }


    // Builds user object from CSV entry given in PVID format
    private class PVIDFormatConsumer implements Function<CSVRecord, User> {

        @Override
        public User apply(CSVRecord record) {
            String name;
            String email;

            email = record.get("personal_email") != null && !record.get("personal_email").equals("") ? record.get("personal_email")
                    : record.get("pv_email");

            name = record.get("first_name") + " " + record.get("last_name");

            if (name == null || email == null)
                System.err.println("Mail/Name missing from entry: " + record.toString());

            return new User(name, email);
        }
    }

    // Builds user object from CSV entry given in BARSYS format
    private class BARSYSFormatConsumer implements Function<CSVRecord, User> {

        @Override
        public User apply(CSVRecord record) {
            String name;
            String email;

            email = record.get("mail");

            name = record.get("display_name");

            if (name == null || email == null)
                System.err.println("Mail/Name missing from entry: " + record.toString());

            return new User(name, email);
        }
    }

    // Builds user object from CSV entry given in name / email format
    private class DEFAULTFormatConsumer implements Function<CSVRecord, User> {

        @Override
        public User apply(CSVRecord record) {
            String name;
            String email;

            email = record.get("email");

            name = record.get("name");

            if (name == null || email == null)
                System.err.println("Mail/Name missing from entry: " + record.toString());

            return new User(name, email);
        }
    }
}
