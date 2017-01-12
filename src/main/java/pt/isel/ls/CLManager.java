package pt.isel.ls;
import pt.isel.ls.Helpers.*;

import java.util.Arrays;

public class CLManager {

    // create a tag
    // git tag -a 0.0.0 -m "Version 0.0.0"
    // git push origin 0.0.0

    // clone to a tag
    // git clone https://github.com/isel-leic-ls/1617-1-LI41N-G15.git
    // git tag -l
    // git checkout tags/<tag_name>

    // command line call example (set env vars first)
    // java -cp "vendor/main/*:build/classes/main" pt.isel.ls.CLManager GET /checklists

    public static void main(String[] args) throws Exception {

        try {
            RequestParser par = new RequestParser(args);
            //System.out.println(Arrays.toString(args));
            new ProcessCmd().doProcess(par);
        } catch (Exception e){
            System.out.println("Error> " + e.getMessage());
        }

    }
}

