package WichtelGenerator.randomizer;

public class User implements Comparable<User> {

    private String name;
    private String mail;
    private User killer;
    private User victim;

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public User getKiller() {
        return killer;
    }

    public void setKiller(User killer) {
        this.killer = killer;
    }

    public User getVictim() {
        return victim;
    }

    public void setVictim(User victim) {
        this.victim = victim;
    }

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return name + "\t" + mail;
    }

    @Override
    public int compareTo(User o) {
        return this.getName().compareTo(o.getName());
    }
}
