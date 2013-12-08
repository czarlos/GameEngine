package gameObject;

public interface IStats {
    public abstract void removeStat (String statName);

    public abstract void changeStatName (String oldName, String newName);

    public abstract void addStat (Stat newStat);

    public abstract boolean containsStat (String name);
}
