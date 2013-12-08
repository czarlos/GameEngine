package dialog.dialogs.tableModels;

import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.Stats;
import grid.GridConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import dialog.Selector;


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
        Object[] ret = new Object[myColumnNames.length + 1];
        
        ret[0] = "New Unit";
        ret[1] = new File("resources/grass.png");
        ret[2] = new Selector(myED.getNames(GridConstants.TEAM), defaultAffiliation);
        Stats stats = new Stats();
        for (String s : GridConstants.DEFAULTSTATARRAY) {
            stats.addStat(new Stat(s));
        }
        stats.modExisting("movement", 4);
        stats.modExisting("attack", 2);
        stats.modExisting("defense", 2);
        stats.modExisting("health", 15);
        stats.modExisting("max health", 15);
        ret[3] = new Stats();
        ret[4] = -1;
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<GameUnit> list = (List<GameUnit>) object;
        defaultAffiliation = list.get(0).getAffiliation();
        for (int i = 0; i < list.size(); i++){
            Object[] array = new Object[myColumnNames.length + 1];
            
            GameUnit gu = list.get(i);
            array[0] = gu.getName();
            array[1] = new File(gu.getImagePath());
            array[2] = new Selector(myED.getNames(GridConstants.TEAM), gu.getAffiliation());
            array[3] = gu.getStats();
            array[4] = i;
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
            gu.setLastIndex((int) row[4]);
            ret.add(gu);
        }
        return ret;
    }
}
