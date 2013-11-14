package stage;

import grid.Grid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Condition {
    @JsonProperty
    protected Map<String, String> myData;
    protected List<String> neededData;

    public Condition () {
        myData = new HashMap<String, String>();
        neededData = new ArrayList<String>();
    }

    public void addData (String key, String data) {
        myData.put(key, data);
    }

    public List<String> getNeededData () {
        return neededData;
    }

    abstract boolean isFulfilled (Grid grid);
}
