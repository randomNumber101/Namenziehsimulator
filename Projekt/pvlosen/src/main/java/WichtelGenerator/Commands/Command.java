package WichtelGenerator.Commands;

public abstract class Command {
    protected String key;
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
