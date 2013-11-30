package gameObject.action;

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

    public void addAction (Action newAction) {
        newAction.setMasterIndex(myActionList.size());
        myActionList.add(newAction);
    }

    public void setAction (int actionIndex, Action newAction) {
        newAction.setMasterIndex(actionIndex);
        myActionList.set(actionIndex, newAction);
    }

    public void setActionList (List<Action> newActionList) {
        for (int i = 0; i < newActionList.size(); i++) {
            newActionList.get(i).setMasterIndex(i);
        }

        myActionList = newActionList;
    }

    public void removeAction (int removeIndex) {
        myActionList.remove(removeIndex);
        // TODO: Update master indices
    }

    public Action getAction (int actionIndex) {
        return myActionList.get(actionIndex);
    }

    public Action getAction (String actionName) {
        for (int i = 0; i < myActionList.size(); i++) {
            if (myActionList.get(i).getName().equals(actionName)) { return myActionList
                    .get(i); }
        }

        return null;
    }

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
            if (Math.abs(myActionList.get(i).getMasterIndex() - i) != compare) {
                removedIndices.add(myActionList.get(i).getMasterIndex() - 1);
            }
        }

        return removedIndices;
    }

    public Map<Integer, Integer> updateIndices () {
        Map<Integer, Integer> translationTable = new HashMap<>();

        for (int i = 0; i < myActionList.size(); i++) {
            translationTable.put(myActionList.get(i).getMasterIndex(), i);
            myActionList.get(i).setMasterIndex(i);
        }

        return translationTable;
    }
}
