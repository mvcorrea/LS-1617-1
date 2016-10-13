package pt.isel.ls.Helpers;

import pt.isel.ls.Exceptions.GenericException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RequestParser {
    public String[] args = null;
    private int cmdLen = 0;
    public String method = "";
    public String[] path = null;
    public HashMap<String,String> params = null;

    Pattern pattMethod = Pattern.compile("GET|POST");
    Pattern pattPath   = Pattern.compile("^/.*");
    Pattern pattParams = Pattern.compile(".*=+.*");

    public RequestParser(String[] args) throws GenericException {
        this.args = args;
        this.cmdLen = args.length;
        if(cmdLen > 1){
            if(!pattMethod.matcher(args[0]).matches())
                throw new GenericException("RequestParser: ["+this.method+"] unknown method");

            this.method = args[0];

            if(!pattPath.matcher(args[1]).matches())
                throw new GenericException("RequestParser: ["+this.path+"] path must start with /");

            String[] pathParts = args[1].split("\\/");
            this.path = Arrays.copyOfRange(pathParts, 1, pathParts.length);
        }
        if(cmdLen > 2){
            if(this.path.length < 1)    // if command has parameters MUST have a path
                throw new GenericException("RequestParser: ["+this.method+"] unknown path");

            this.params = parseParams(args[2]);
        }
        System.out.println(toString());
    }

    public String matchString(){    // just the method and path (to identify the command)
        //System.out.println("["+String.join(" ", args)+"] " + args.length);
        return args.length != 0 ? String.join(" ", Arrays.copyOfRange(this.args, 0, 2)) : "";
    }

    public HashMap<String,String> parseParams(String parameters) throws GenericException {
        HashMap<String,String> out = new HashMap<>();
        if(!parameters.matches(".*=+.*"))
            throw new GenericException("RequestParser: ["+ parameters +"] unable to parse params");
        Arrays.asList(parameters.split("&")).forEach(x -> {
            String[] tmp = x.split("=");
            out.put(tmp[0], tmp[1]);
        });
        return out;
    }

    public String getMethod(){ return this.method; }
    public String[] getPath(){ return this.path; }
    public HashMap<String,String> getParams(){ return this.params; }

    @Override
    public String toString() {
        return "RequestParser { " +
                "method='" + method + '\'' +
                ", path=" + Arrays.toString(path) +
                ", params=" + params +
                " }";
    }
}

