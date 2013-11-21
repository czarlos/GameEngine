package controller.actions;

public class CommandShell {
    private String myCommandName;
    private Object[] myArgs;
    
    public CommandShell(String commandName,Object[] args){
        myCommandName=commandName;
        myArgs=args;
    }
    
    public String getCommandName(){
        return myCommandName;
    }
    
    public Object[] getArguments(){
        return myArgs;
    }
    
}
