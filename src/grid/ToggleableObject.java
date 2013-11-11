package grid;

public class ToggleableObject extends GameObject {
    GameObject myNextState;

    public ToggleableObject (String name, String imagePath, GameObject nextState) {
        super(name);
        myNextState = nextState;
    }

    public GameObject getNextState () {
        return myNextState;
    }

    public void setNextState (GameObject nextState) {
        myNextState = nextState;
    }

}
