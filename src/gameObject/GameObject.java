package gameObject;

import java.awt.Graphics;


public class GameObject {
    private String myName;
    private String myImagePath;

    public GameObject (String name, String imagePath) {
        myName = name;
        myImagePath = imagePath;
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
}
