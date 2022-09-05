package WichtelGenerator.MailCreator;
import WichtelGenerator.fileIO.SettingLoader;
import WichtelGenerator.randomizer.Randomizer;
import WichtelGenerator.randomizer.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class MailCreator {

    private String from;
    private String pw;
    private String smtpAddress;
    private Properties properties = System.getProperties();
    private Session session;

    private File mailFile;

    private final String regUName = "#u_name"; //the user
    private final String regVName = "#v_name"; //the users victim
    private final String regKName = "#k_name"; //the users killer
    private final String regList =  "#u_list"; //the user list

    private static MailCreator mInstance;

    private MailCreator() {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        //properties.put("mail.smtp.host", "outlook.office365.com");
        properties.put("mail.smtp.port", "587");
        session = Session.getDefaultInstance(properties);
    }


    public static MailCreator getInstance() {
        if (mInstance == null) {
            mInstance = new MailCreator();
        }
        return mInstance;
    }

    public void sendTest(String msg) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(from));
            message.setSubject("Test");
            message.setText(msg);

            Transport.send(message, from, pw);
            System.out.println("Message sent..");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendNotification(User user) {
        if (pw == null || from == null) {
            System.err.println("Could not send mails. No mail address or password loaded.");
        }

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
            message.setSubject("User Information!");
            message.setText(getMessage(user));

            Transport.send(message, from, pw);
            System.out.println("Message sent..");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(User u) {

        if (mailFile != null) {
            String o = null;
            try {
                o = parseMailFile(u);
            } catch (IOException e) {
                System.err.println("File could not be decoded!");
            }
            if (o != null) {
                return o;
            }
        }

        String message =
                "MAIL FILE COULD NOT BE READ. "; // Your standard message


        return message;
    }

    private String parseMailFile(User u) throws IOException {


        String userList = Randomizer.getInstance().getUserListString();
        String out = new String(Files.readAllBytes(mailFile.toPath()));

        out = out.replaceAll(regKName, u.getKiller() != null ? u.getKiller().getName() : "killer_null")
                .replaceAll(regVName, u.getVictim() != null ? u.getVictim().getName() : "victim_null")
                .replaceAll(regUName, u.getName())
                .replaceAll(regList, userList) + "\n";

        return out;
    }


    public void reloadInformation() {
        from = SettingLoader.getInstance().getMailAddress();
        pw = SettingLoader.getInstance().getMailPW();
        smtpAddress = SettingLoader.getInstance().getSmtpAddress();
        if (smtpAddress != null) {
            properties.put("mail.smtp.host", smtpAddress);
        }
        File f = new File(SettingLoader.getInstance().getMailFilePath());
        if (f.exists()) {
            mailFile = f;
        }
    }

    public void setMailFile(File f) {
        mailFile = f;
    }
}
