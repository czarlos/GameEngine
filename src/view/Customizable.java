package view;

import game.ImageManager;
import java.awt.image.BufferedImage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Customizable {
    protected String myName;
    @JsonProperty
    protected String myImagePath;
    protected BufferedImage myImage;
    protected int myLastIndex;

    public Customizable () {

    }

    public int getLastIndex () {
        return myLastIndex;
    }

    public void setLastIndex (int newLastIndex) {
        myLastIndex = newLastIndex;
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
        myImage = ImageManager.getImage(imagePath);
    }

    @JsonIgnore
    public BufferedImage getImage () {
        return myImage;
    }

    public String toString () {
        return myName;
    }
}
