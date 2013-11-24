package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Customizable {
    protected String myName;
    @JsonProperty
    protected String myImagePath;

    public Customizable () {

    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
    }

    @JsonIgnore
    public Image getImage () {
        try {
            return ImageIO.read(new File(myImagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getImagePath () {
        return myImagePath;
    }

    @JsonProperty("imagePath")
    public void setImagePath (String imagePath) {
        myImagePath = imagePath;
    }
}
