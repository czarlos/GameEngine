package gameObject;

public class MasterStats extends Stats {

    public MasterStats () {
    }

    public void setStatValue (String name, Integer value) {
        myStatList.put(name, value);
    }
}
