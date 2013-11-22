package dialog;

public class WeaponTableModel extends GameTableModel{
    
    
    public Object[] addNewRow (Object[] row) {
        
        //Name, Statsmodifier list of actions
        Object[] rowToAdd = {row[0], row[1], row[2]};
        
        list.add(rowToAdd);
        
        return rowToAdd.clone();
    }
    
    //TODO: handle StatsModifier Object
    
    
    

}
