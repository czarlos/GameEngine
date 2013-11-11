package grid;

public class ToggleableObject extends GameObject {
    GameObject myNextState;

    public ToggleableObject (String name, String imagePath, GameObject nextState) {
        super();
        myNextState = nextState;
    }

    public GameObject getNextState () {
        return myNextState;
    }

    public void setNextState (GameObject nextState) {
        myNextState = nextState;
    }

}
