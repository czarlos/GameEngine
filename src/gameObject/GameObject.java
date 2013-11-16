package gameObject;

import java.util.List;
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

    public String getImagePath () {
        return myImagePath;
    }

    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myImagePath == null) ? 0 : myImagePath.hashCode());
        result = prime * result + ((myName == null) ? 0 : myName.hashCode());
        result = prime * result + ((myPassableList == null) ? 0 : myPassableList.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameObject other = (GameObject) obj;
        if (myImagePath == null) {
            if (other.myImagePath != null) return false;
        }
        else if (!myImagePath.equals(other.myImagePath)) return false;
        if (myName == null) {
            if (other.myName != null) return false;
        }
        else if (!myName.equals(other.myName)) return false;
        if (myPassableList == null) {
            if (other.myPassableList != null) return false;
        }
        else if (!myPassableList.equals(other.myPassableList)) return false;
        return true;
    }

}
