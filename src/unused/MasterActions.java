package unused;

import gameObject.action.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MasterActions {
    private static MasterActions masterActions = new MasterActions();
    private List<Action> myActionList;

    private MasterActions () {
        myActionList = new ArrayList<>();
    }

    public static MasterActions getInstance () {
        if (masterActions == null) {
            masterActions = new MasterActions();
        }
        return masterActions;
    }

    public void setActionList (List<Action> newActionList) {
        for (int i = 0; i < newActionList.size(); i++) {
            newActionList.get(i).setLastIndex(i);
        }

        myActionList = newActionList;
    }

    // functionality currently in editordata
    public Action getAction (int actionIndex) {
        return myActionList.get(actionIndex);
    }

    // functionality currently in gamemanager
    public Action getAction (String actionName) {
        for (int i = 0; i < myActionList.size(); i++) {
            if (myActionList.get(i).getName().equals(actionName)) { return myActionList.get(i); }
        }

        return null;
    }

    public int getActionID (String actionName) {
        for (int i = 0; i < myActionList.size(); i++) {
            if (myActionList.get(i).getName().equals(actionName)) { return i; }
        }
        return 0;
    }

    // functionality currently in editordata
    public List<String> getActionNames () {
        List<String> actionNames = new ArrayList<>();

        for (Action action : myActionList) {
            actionNames.add(action.getName());
        }

        return actionNames;
    }

    public List<Integer> getRemoveIndices () {
        List<Integer> removedIndices = new ArrayList<>();
        int compare = 0;

        for (int i = 0; i < myActionList.size(); i++) {
            if (Math.abs(myActionList.get(i).getLastIndex() - i) != compare) {
                removedIndices.add(myActionList.get(i).getLastIndex() - 1);
                compare++;
            }
        }

        return removedIndices;
    }

    public Map<Integer, Integer> updateIndices () {
        Map<Integer, Integer> translationTable = new HashMap<>();

        for (int i = 0; i < myActionList.size(); i++) {
            translationTable.put(myActionList.get(i).getLastIndex(), i);
            myActionList.get(i).setLastIndex(i);
        }

        return translationTable;
    }
}
