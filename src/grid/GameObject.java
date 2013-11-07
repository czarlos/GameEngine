package grid;

import java.awt.Graphics;


public class GameObject {
    private String myName;
    private String myImagePath;
    private int myPassStatus;

    public GameObject (String name, String imagePath, int passStatus) {
        myName = name;
        myImagePath = imagePath;
        myPassStatus = passStatus;
    }

    public void paint (Graphics g) {
        // TODO
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    public String getImagePath () {
        return myImagePath;
    }

    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
    }

    public int getPassStatus () {
        return myPassStatus;
    }

    public void setPassStatus (int passStatus) {
        myPassStatus = passStatus;
    }
}
