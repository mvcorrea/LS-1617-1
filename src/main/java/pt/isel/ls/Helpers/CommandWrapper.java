package pt.isel.ls.Helpers;

public class CommandWrapper {
    CommandInterface x;
    public CommandWrapper(CommandInterface x){ this.x = x; }
    public CommandInterface getCmd(){ return x; }
}
