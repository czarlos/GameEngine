package gameObject;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import view.Drawable;


@JsonAutoDetect
public class GameObject extends Drawable {
    protected List<String> myPassableList;

    public GameObject () {
        setPassableList(new java.util.ArrayList<String>());
    }

    public boolean isPassable (GameObject unit) {
        for (String object : myPassableList) {
            if (object.equals(unit.getName())) { return true; }
        }

        return false;
    }

    public void addPassable (String passable) {
        myPassableList.add(passable);
    }

    public void setPassableList (List<String> passables) {
        myPassableList = passables;
    }

    public List<String> getPassableList () {
        return myPassableList;
    }

    @Override
    public Map<String, String> getData () {
        // TODO: Needs to implement this for everything that extends GameObject (for GUI editing purposes)
        return null;
    }

}
