package dialog.dialogs.tableModels;

import gameObject.GameUnit;
import gameObject.Stats;
import grid.GridConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;


/**
 * 
 * @author brooksmershon
 * 
 */
@SuppressWarnings("serial")
public class UnitTableModel extends GameTableModel {

    String defaultAffiliation;
    EditorData myED;

    /**
     * Column names: name, Graphic, Affiliation, Stats
     */
    public UnitTableModel (EditorData ed) {
        String[] names = { "Name", "Graphic", "Affiliation", "Stats" };
        myName = GridConstants.GAMEUNIT;
        setColumnNames(names);
        myED = ed;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = "New Unit";
        ret[1] = new File("resources/grass.png");
        ret[2] = new Selector(myED.getNames(GridConstants.TEAM), defaultAffiliation);
        ret[3] = new Stats();
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<GameUnit> list = (List<GameUnit>) object;
        defaultAffiliation = list.get(0).getAffiliation();
        for (GameUnit gu : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = gu.getName();
            array[1] = new File(gu.getImagePath());
            array[2] = new Selector(myED.getNames(GridConstants.TEAM), gu.getAffiliation());
            array[3] = gu.getStats();
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<GameUnit> ret = new ArrayList<GameUnit>();
        for (Object[] row : myList) {
            GameUnit gu = new GameUnit();
            gu.setName((String) row[0]);
            gu.setImagePath((String) ((File) row[1]).getPath());
            gu.setAffiliation((String) ((Selector) row[2]).getValue());
            gu.setStats((Stats) row[3]);
            ret.add(gu);
        }
        return ret;
    }
}
