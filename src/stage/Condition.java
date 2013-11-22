package stage;

import java.util.ArrayList;
import java.util.HashMap;
import grid.Grid;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public abstract class Condition extends Customizable {
    abstract boolean isFulfilled (Grid grid);

    public Condition () {
        myData = new HashMap<String, String>();
        neededData = new ArrayList<String>();
    }
}
