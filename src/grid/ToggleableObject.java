package grid;

public class ToggleableObject extends GameObject {
    GameObject myNextState;

    public ToggleableObject (String name, String imagePath, int passStatus, GameObject nextState) {
        super(name, imagePath, passStatus);
        myNextState = nextState;
    }

    public GameObject getNextState () {
        return myNextState;
    }

    public void setNextState (GameObject nextState) {
        myNextState = nextState;
    }

}
