package stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Condition {
    abstract boolean isFulfilled (Stage stage);

    @JsonProperty
    protected Map<String, String> myData;
    protected List<String> myNeededData;

    public Condition () {
        myData = new HashMap<String, String>();
        myNeededData = new ArrayList<String>();
    }

    public void setData (Map<String, String> data) {
        myData = data;
    }

    @JsonIgnore
    public List<String> getNeededData () {
        return myNeededData;
    }
}
