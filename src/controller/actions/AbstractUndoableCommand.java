package controller.actions;

public abstract class AbstractUndoableCommand implements UndoableCommand {
    
    @Override
    public void redo(){
        execute();
    }
}
