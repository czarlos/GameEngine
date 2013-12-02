package controller.actions;

/**
 * Interface to encapsulate actions that can be executed by other objects.
 * 
 */
public interface Command {

	/**
	 * Execute a predefined action.
	 */
	public void execute();
}
