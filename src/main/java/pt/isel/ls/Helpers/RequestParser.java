package pt.isel.ls.Helpers;

import pt.isel.ls.Debug;
import pt.isel.ls.Exceptions.AppException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class RequestParser {
    public String[] args = null;
    private int cmdLen = 0;
    public String method = "";
    public String[] path = null;
    public HashMap<String,String> params = null;
    public HashMap<String,String> headers = null;
    //headers.put("accept", "application/json");

    Pattern pattMethod = Pattern.compile("GET|POST|DELETE|OPTIONS|EXIT");
    Pattern parrHeadKeys = Pattern.compile("accept|file-name|accept-language");
    Pattern parrHeadAccept = Pattern.compile("text\\/plain|text\\/html|application\\/json");
    Pattern pattPath   = Pattern.compile("^/.*");
    Pattern pattParams = Pattern.compile(".*=+.*");
    Pattern pattHeaders = Pattern.compile(".*:+.*");

    public RequestParser(String[] args) throws AppException {
        this.args = args;
        this.cmdLen = args.length;
        try {

            if(cmdLen > 1){ // MUST: Method + Path
                if(!pattMethod.matcher(args[0]).matches())  // Parse the Method
                    throw new AppException("RequestParser: [" + args[0] + "] unknown method");
                else
                    this.method = args[0];

                if(!pattPath.matcher(args[1]).matches())    // Parse the Path
                    throw new AppException("RequestParser: [" + args[1] + "] path must start with /");
                else {
                    String[] pathParts = args[1].split("\\/");
                    //System.out.println(String.join("|", pathParts) + " len: "+ pathParts.length);
                    if(pathParts.length > 1)
                        this.path = Arrays.copyOfRange(pathParts, 1, pathParts.length);
                    else
                        this.path = new String[0];
                }

                headers = new HashMap<>();
                headers.put("accept", "application/json"); // default header accept format < ----------------------------------------------
            }
            if(cmdLen > 2){  // have parameters or headers

                if(cmdLen == 3){ // {method} {path} {parameters}
                    if(!(pattHeaders.matcher(args[2]).matches() || pattParams.matcher(args[2]).matches()))
                        throw new AppException("RequestParser: [" + args[2] + "] unable to match header/params");
                    if(pattHeaders.matcher(args[2]).matches())
                        this.headers = parseHeaders(args[2]);
                    if(pattParams.matcher(args[2]).matches())
                        this.params  = parseParams(args[2]);
                } else if(cmdLen == 4){ // {method} {path} {headers} {parameters}
                    if(!(pattHeaders.matcher(args[2]).matches() && pattParams.matcher(args[3]).matches()))
                        throw new AppException("RequestParser: [" + args[2] +" "+ args[3] + "] unable to match header/params");
                    if(pattHeaders.matcher(args[2]).matches())
                        this.headers = parseHeaders(args[2]);
                    if(pattParams.matcher(args[3]).matches())
                        this.params  = parseParams(args[3]);
                } else throw new AppException("RequestParser: [" + args[2] +" "+ args[3] +"] unable to parse header/params");

            }
        } catch (Exception e){
            throw new AppException("RequestParser: " + e.getMessage());
        }


        // auto show the request object (for debugging)
        if(Debug.ON) System.out.println(toString());
    }

    public String matchString(){    // just the method and path (to identify the command)
        //System.out.println("["+String.join(" ", args)+"] " + args.length);
        return args.length != 0 ? String.join(" ", Arrays.copyOfRange(this.args, 0, 2)) : "";
    }

    public HashMap<String,String> parseParams(String parameters) throws AppException {
        HashMap<String,String> out = new HashMap<>();
        if(!parameters.matches(".*=+.*"))
            throw new AppException("RequestParser: ["+ parameters +"] unable to parse params");
        Arrays.asList(parameters.split("&")).forEach(x -> {
            String[] tmp = x.split("=");
            out.put(tmp[0], tmp[1]);
        });
        return out;
    }

    public HashMap<String,String> parseHeaders(String headers) throws AppException { // optional component
        HashMap<String,String> out = new HashMap<>();
        if(!headers.matches(".*:+.*"))
            throw new AppException("RequestParser: ["+ headers +"] unable to parse headers");
        Arrays.asList(headers.split("\\|")).forEach(x -> {
            String[] tmp = x.split(":");
            out.put(tmp[0], tmp[1]);
        });
        return out;
    }


    public String getMethod(){ return this.method; }
    public String[] getPath(){ return this.path; }
    public HashMap<String,String> getParams(){ return this.params; }
    public HashMap<String,String> getHeaders(){ return this.headers; }

    @Override
    public String toString() {
        return "RequestParser { " +
                "method='" + method + '\'' +
                ", path=" + Arrays.toString(path) +
                ", headers=" + headers +
                ", params=" + params +
                " }";
    }
}

