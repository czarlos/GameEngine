package controller.actions;

/**
 * Class to encapsulate a command to be run and the arguments it takes. 
 * This class contains the name of a class that represents that command 
 * to perform and any arguments this class will take. It is meant to be used
 * with classes that implement Command.
 *
 */
public class CommandShell {
    private String myCommandName;
    private Object[] myArgs;

    /**
     * Instatiate command shell
     * @param commandName Class name to represent command to be executed.
     * @param args Any arguments that command needs to run.
     */
    public CommandShell (String commandName, Object[] args) {
        myCommandName = commandName;
        myArgs = args;
    }

    /**
     * Retrieve name of class to be executed.
     * @return String representing class name.
     */
    public String getCommandName () {
        return myCommandName;
    }

    /**
     * Get the object array that contains any arguments this command needs.
     * @return Array of objects to be be used as arguments when instantiating 
     * the command this class represents.
     */
    public Object[] getArguments () {
        return myArgs;
    }

}
