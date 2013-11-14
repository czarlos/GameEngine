package stage;

import java.util.Map;
import grid.Grid;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public abstract class Condition extends Customizable {
    abstract boolean isFulfilled (Grid grid);

    public Map<String, String> getData () {
        return myData;
    }
}
