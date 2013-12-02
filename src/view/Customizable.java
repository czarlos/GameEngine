package view;

import java.awt.image.BufferedImage;
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
	protected BufferedImage myImage;

	public Customizable() {

	}

	public String getName() {
		return myName;
	}

	public void setName(String name) {
		myName = name;
	}

	public String getImagePath() {
		return myImagePath;
	}

	public void setImagePath(String imagePath) {
		myImagePath = imagePath;
		try {
			myImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@JsonIgnore
	public BufferedImage getImage() {
		return myImage;
	}
}
