package dialog.dialogs.tableModels;

import grid.GridConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import stage.WinCondition;
import team.Team;


@SuppressWarnings("serial")
public class TeamTableModel extends GameTableModel {

    public TeamTableModel () {
        String[] names = { "Name", "Graphic", "Gold", "Win Conditions", "Human?" };
        myName = GridConstants.TEAM;
        setColumnNames(names);
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<Team> list = (List<Team>) object;
        for (int i = 0; i < list.size(); i++) {

            Object[] array = new Object[myColumnNames.length + 1];
            Team t = (Team) list.get(i);
            array[0] = t.getName();
            array[1] = new File(t.getImagePath());
            array[2] = t.getGold();
            array[3] = t.getWinCondition();
            array[4] = t.isHuman();
            array[5] = i;
            myList.add(array);
        }
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        return (column < 5);
    }

    @Override
    public List<?> getObject () {
        List<Team> ret = new ArrayList<Team>();
        for (Object[] row : myList) {
            Team t = new Team();
            t.setName((String) row[0]);
            t.setImagePath((String) ((File) row[1]).getPath());
            t.setGold((int) row[2]);
            t.setWinCondition((WinCondition) row[3]);
            t.setIsHuman((boolean) row[4]);
            t.setEditingID((int) row[5]);
            ret.add(t);
        }

        // checking to make sure there is always one list.
        if (ret.size() == 0) {
            Team t = new Team();
            t.setName("default");
            t.setImagePath("resources/Grass.png");
            t.setGold(0);
            t.setWinCondition(new WinCondition());
            t.setIsHuman(true);
            t.setEditingID(0);
            ret.add(t);
        }
        return ret;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length];
        array[0] = "New Team";
        array[1] = "resources/Grass.png";
        array[2] = 0;
        array[3] = new WinCondition();
        array[4] = false;

        return array;
    }
}
