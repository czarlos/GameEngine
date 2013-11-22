package dialog;

public class StatsTableModel extends GameTableModel{

    @Override
    public Object[] addNewRow (Object[] row) {
        
        Object[] rowToAdd = {row[0], row[1]};
        
        list.add(rowToAdd);
        
        return rowToAdd.clone();
    }

}
