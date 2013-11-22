package dialog;

public class StatsTableModel extends GameTableModel{

    @Override
    public Object[] addNewRow (Object[] row) {
        
        Object[] rowToAdd = {row[0], row[1]};
        
        list.add(rowToAdd);
        
        return rowToAdd.clone();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return col < 1 ? false : true;
     }
     
}
