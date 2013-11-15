package stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import grid.Grid;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Condition extends Customizable {

    @JsonProperty
    protected Map<String, String> myData;
    protected List<String> neededData;

    abstract boolean isFulfilled (Grid grid);

    public Condition () {
        myData = new HashMap<String, String>();
        neededData = new ArrayList<String>();
    }

    public Map<String, String> getData () {
        return myData;
    }
}
