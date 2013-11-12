package engine;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public abstract class Condition {
    @JsonProperty
    protected Map<String, String> myData;
    
    public Condition () {
        myData = new HashMap<String, String>();
    }

    public void addData (String key, String data) {
        myData.put(key, data);
    }

    abstract boolean isFulfilled ();
}
