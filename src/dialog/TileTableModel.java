package dialog;

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


public class TileTableModel extends GameTableModel{

    public TileTableModel(){
        String[] names = {"Name", "Graphic", "Move Cost", "StatsModifiers"};

        setColumnNames(names);
    }

    public void addPreviouslyDefined (List<Customizable> tiles) {

        for (Object tile : tiles) {
            Object[] array = new Object[myColumnNames.length];

            Tile t = (Tile) tile;

            array[0] = t.getName();
            array[1] = new File(t.getImagePath());
            array[2] = t.getMoveCost();
            array[3] = t.getStatMods();

            myList.add(array);        

        }
    }

    public Object getObject (int ID) {
        if (ID < myColumnNames.length) {
            Object[] current = myList.get(ID);
            Tile t = new Tile();
            t.setName((String) current[0]);
            t.setImageAndPath((String) ((File) current[1]).getPath());
            t.setMoveCost((int) current[2]);
            t.setStatMods((Map<String, Double>) current[3]);

            return t;
        }

        return null;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return true;
        
    }
    
    
    
}
