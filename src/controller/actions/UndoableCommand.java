package controller.actions;

/**
 * Interface to define encapsulated actions that can be done and redone.
 *
 */
public interface UndoableCommand extends Command{
    
    /**
     * Undo a previously done action.
     */
    public abstract void undo();
    
    /**
     * Redo a previously undone action.
     */
    public abstract void redo();
}
