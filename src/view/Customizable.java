package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;


public abstract class Customizable {
    @JsonProperty
    protected Map<String, String> myData;
    protected List<String> neededData;
    protected String myName;

    public Customizable () {
        myData = new HashMap<String, String>();
        neededData = new ArrayList<String>();
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    // might never be used.
    public void addData (String key, String data) {
        myData.put(key, data);
    }

    public void setData (Map<String, String> data) {
        myData = data;
    }

    public List<String> getNeededData () {
        return neededData;
    }

    public abstract Map<String, String> getData ();
}
