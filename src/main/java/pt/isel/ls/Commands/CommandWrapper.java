package pt.isel.ls.Commands;

public class CommandWrapper {
    Command_Interface x;
    public CommandWrapper(Command_Interface x){ this.x = x; }
    public Command_Interface getCmd(){ return x; }
}
