package pt.isel.ls;
import pt.isel.ls.Helpers.*;

public class CLManager {

    // command line call example (set env vars first)
    // java -cp "vendor/main/*:build/classes/main" pt.isel.ls.CLManager GET /checklists
    public static void main(String[] args) throws Exception {

        try {
            RequestParser par = new RequestParser(args);
            new ProcessCmd().doProcess(par);
        } catch (Exception e){
            System.out.println("Error> " + e.getMessage());
        }

//
//        //CommandMatcher commands = new CommandMatcher();
//        CommandWrapper cmd = commands.matchCommand(args);
//        //System.out.println(cmd.getCmd().toString());    // tenho o obj associado ao comando chamado
//
////        RequestParser par = new RequestParser(args);
////        System.out.println(par.toString());             // tenho  o objeto comando
//        //System.out.println();
//
//        DBConn cc = new DBConn();
//        cmd.getCmd().process(cc.getDataSource(), par);  // command query
    }
}

