package controller.editor;

import java.util.Map;
import grid.Coordinate;
import view.canvas.GridMouseListener;


public class GridController implements GridMouseListener {

    private Coordinate mySelectedCoordinate;
  //  private Map<String,GridCommand> m

    @Override
    public void gridClicked (Coordinate c) {
        mySelectedCoordinate = c;
    }

}
