package controller.actions;

/**
 * Abstract class to wrap Undoable command. 
 * Implements redo by calling execute again.
 *
 */
public abstract class AbstractUndoableCommand implements UndoableCommand {

    @Override
    public void redo () {
        execute();
    }
}
