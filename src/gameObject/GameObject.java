package gameObject;

import grid.ImageManager;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import view.Drawable;


/**
 * GameObject class. Stuff like trees.
 * 
 * @author Kevin, Ken
 * 
 */
@JsonAutoDetect
public class GameObject extends Drawable {
    protected List<String> myPassableList;
    protected BufferedImage myImage;
    
    public GameObject () {
    }

    /**
     * Checks if a unit can pass through the object
     * 
     * @param unit - GameObject that is moving
     * @return - boolean of if unit can pass through
     */
    public boolean isPassable (GameObject unit) {
        return myPassableList.contains(unit.getName()) ||
               myPassableList.contains(GameObjectConstants.DEFAULT_PASS_EVERYTHING);
    }

    /**
     * Adds a new object that can be passed through
     * 
     * @param passable - String of object name that can pass
     */
    public void addPassable (String passable) {
        myPassableList.add(passable);
    }

    public void setPassableList (List<String> passables) {
        myPassableList = passables;
    }

    public List<String> getPassableList () {
        return myPassableList;
    }

    public Map<String, String> getData () {
        // TODO: Needs to implement this for everything that extends GameObject (for GUI editing
        // purposes)
        return null;
    }

    public String getImagePath () {
        return myImagePath;
    }
    
    @JsonProperty("imagePath")
    public void setImageAndPath (String imagePath) {
        myImagePath = imagePath;
        try {
            myImage = ImageManager.addImage(imagePath);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
