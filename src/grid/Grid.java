package grid;

import java.util.HashMap;
import java.util.Map;


public class Grid {
    private int myCol;
    private int myRow;
    private Map<Coordinate, Tile> myTileMap;
    private Map<GameObject, Coordinate> myObjectMap;

    public Grid (int col, int row) {
        myCol = col;
        myRow = row;
        myTileMap = new HashMap<Coordinate, Tile>();
        myObjectMap = new HashMap<GameObject, Coordinate>();

        initGrid();
    }

    private void initGrid () {
        initTiles();
    }

    private void initTiles () {
        for (int i = 0; i < myCol; i++) {
            for (int j = 0; j < myRow; j++) {
                myTileMap.put(new Coordinate(i, j), new Tile());
            }
        }
    }

}
