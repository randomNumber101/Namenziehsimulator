package ImageCodeInjector.Command;

public abstract class Command {
    public String key;
    protected int maxArgCount;

    public abstract void run(String[] args);

    public String getKey() {
        return key;
    }

    public int getMaxArgCount() {
        return maxArgCount;
    }

    public abstract String getDescription();
    public abstract String getUsage();
}
