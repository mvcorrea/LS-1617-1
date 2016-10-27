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

    }
}

