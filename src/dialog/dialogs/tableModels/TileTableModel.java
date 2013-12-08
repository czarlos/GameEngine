package dialog.dialogs.tableModels;

import gameObject.Stats;
import grid.GridConstants;
import grid.Tile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;


/**
 * 
 * @author brooksmershon
 * @author Leevi
 * 
 */

@SuppressWarnings("serial")
public class TileTableModel extends GameTableModel {

    /**
     * Column names: Name, Graphic, Move Cost, Stats
     */
    public TileTableModel (EditorData ED) {
        String[] names = { "Name", "Graphic", "Move Cost", "Stats" };
        myName = GridConstants.TILE;
        setColumnNames(names);
        myED = ED;
    }

    @SuppressWarnings("unchecked")
    public void loadObject (Object object) {
        List<Tile> list = (List<Tile>) object;
        for(int i = 0; i< list.size(); i++){
            Object[] array = new Object[myColumnNames.length + 1];

            Tile t = list.get(i);

            array[0] = t.getName();
            array[1] = new File(t.getImagePath());
            array[2] = t.getMoveCost();
            array[3] = t.getStats();
            array[4] = i;

            addNewRow(array);           
        }
    }

    public List<?> getObject () {
        List<Tile> list = new ArrayList<Tile>();
        for (Object[] row : myList) {
            Tile t = new Tile();
            t.setName((String) row[0]);
            t.setImagePath((String) ((File) row[1]).getPath());
            t.setMoveCost((int) row[2]);
            t.setStats((Stats) row[3]);
            t.setLastIndex((int) row[4]);
            list.add(t);
        }

        return list;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length + 1];

        array[0] = "New Tile";
        array[1] = new File("resources/grass.png");
        array[2] = 1;
        array[3] = new Stats();
        array[4] = -1;

        return array;
    }
}
