package dialog.dialogs.tableModels;

import gameObject.Stats;
import grid.GridConstants;
import grid.Tile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author brooksmershon
 * @author Leevi
 * 
 */

@SuppressWarnings("serial")
public class TileTableModel extends MultipleTableModel {

    public TileTableModel () {
        String[] names = { "Name", "Graphic", "Move Cost", "StatsModifiers" };
        myName = GridConstants.TILE;
        setColumnNames(names);
    }

    public void addObjects (List<?> tiles) {

        for (Object tile : tiles) {
            Object[] array = new Object[myColumnNames.length];

            Tile t = (Tile) tile;

            array[0] = t.getName();
            array[1] = new File(t.getImagePath());
            array[2] = t.getMoveCost();
            array[3] = t.getStats();

            addNewRow(array);
        }
    }

    public List<?> getObjects () {

        List<Tile> list = new ArrayList<Tile>();
        for (Object[] row : myList) {
            Tile t = new Tile();
            t.setName((String) row[0]);
            t.setImagePath((String) ((File) row[1]).getPath());
            t.setMoveCost((int) row[2]);
            t.setStats((Stats) row[3]);
            list.add(t);
        }

        return list;
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        return true;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length];

        array[0] = "New Tile";
        array[1] = new File("resources/grass.png");
        array[2] = 1;
        array[3] = new Stats();

        return array;
    }
}
