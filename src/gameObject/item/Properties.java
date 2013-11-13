package gameObject.item;

/**
 * Properties are a part of gameunits that are not stats but are
 * important aspects of a unit that can be change and affected by other
 * gameunits (such as health). Properties can also be affected by consumables.
 * 
 * @author carlosreyes
 * 
 */
public class Properties {

    private double myHealth;
    private double myExperience;

    public Properties (int health, int experience) {
        myHealth = health;
        myExperience = experience;
    }

    public double getHealth () {
        return myHealth;
    }

    public void setHealth (double myHealth) {
        this.myHealth = myHealth;
    }

    public double getExperience () {
        return myExperience;
    }

    public void setExperience (double myExperience) {
        this.myExperience = myExperience;
    }

}
