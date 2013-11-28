package dialog;

import gameObject.Stats;
import grid.Tile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import view.Customizable;


/**
 * 
 * @author brooksmershon
 * 
 */

public class TileTableModel extends GameTableModel {

    public TileTableModel () {
        String[] names = { "Name", "Graphic", "Move Cost", "StatsModifiers" };
        myName = "Tile";
        setColumnNames(names);
    }

    public void addPreviouslyDefined (List<Customizable> tiles) {

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

    public List<Customizable> getObjects () {

        List<Customizable> list = new ArrayList<Customizable>();
        for (Object[] row : myList) {
            Tile t = new Tile();
            t.setName((String) row[0]);
            t.setImageAndPath((String) ((File) row[1]).getPath());
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

}