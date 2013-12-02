package gameObject;

import gameObject.action.Action;
import grid.GridConstants;
import grid.ImageManager;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import view.Customizable;
import view.Drawable;


/**
 * GameObject class. Stuff like trees.
 * 
 * @author Kevin, Ken
 * 
 */
@JsonAutoDetect
public class GameObject extends Customizable implements Drawable {
    private List<String> myPassableList;
    protected List<String> myDisplayData;
    protected boolean isActive;

    public GameObject () {
        myDisplayData = new ArrayList<String>();
        myPassableList = new ArrayList<String>();
    }

    public void setActive (boolean active) {
        isActive = active;
        myImage = active ? ImageManager.getHightlightedTileImage(myImagePath)
                        : ImageManager.getTileImage(myImagePath);
    }

    public List<String> getInfo () {
        return myDisplayData;
    }

    public void setInfo (List<String> info) {
        myDisplayData = info;
    }

    /**
     * Checks if a unit can pass through the object
     * 
     * @param unit
     *        - GameUnit that is moving
     * @return - boolean of if unit can pass through
     */

    public boolean isPassable (GameUnit unit) {
        return myPassableList.contains(unit.getName())
               || myPassableList
                       .contains(GridConstants.DEFAULT_PASS_EVERYTHING);
    }

    /**
     * Adds a new object that can be passed through
     * 
     * @param passable
     *        - String of object name that can pass
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

    /**
     * Generates the List of Strings that the unit will display to the user
     */
    public void generateDisplayData () {
        List<String> displayData = new ArrayList<>();
        displayData.add("Name: " + myName);
        setDisplayData(displayData);
    }

    public List<String> getDisplayData () {
        return myDisplayData;
    }

    public void setDisplayData (List<String> displayData) {
        myDisplayData = displayData;
    }

    public Action getInteraction () {
        return null;
    };

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        g.drawImage(getImage(), x, y, width, height, null);
    }
}
