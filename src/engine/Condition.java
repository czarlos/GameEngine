package engine;

import java.util.HashMap;
import java.util.Map;


public abstract class Condition {
    Map<String, String> myData;

    public Condition () {
        myData = new HashMap<String, String>();
    }

    public void addData (String key, String data) {
        myData.put(key, data);
    }

    abstract boolean isFulfilled ();
}
