package view;

import grid.ImageManager;
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

    public Customizable () {

    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    public void setImageandPath (String imagePath) {
        myImagePath=imagePath;
        myImage = ImageManager.getTileImage(imagePath);
    }

    public String getImagePath () {
        return myImagePath;
    }

    @JsonProperty("imagePath")
    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
    }

    @JsonIgnore
    public BufferedImage getImage () {
        return myImage;
    }
}
