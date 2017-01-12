package pt.isel.ls.Helpers.WebServer;


import com.google.common.base.Joiner;
import org.slf4j.Logger;
import pt.isel.ls.Debug;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.WEBException;
import pt.isel.ls.Helpers.CommandMatcher;
import pt.isel.ls.Helpers.RequestParser;
import pt.isel.ls.ProcessCmd;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

public class WebParser extends HttpServlet {

    Logger logger;
    private CommandMatcher commands = new CommandMatcher();
    private ProcessCmd cmd;
    int createdId;


    public WebParser() { }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(req.getRequestURI().matches("/favicon.*")) return;
        logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
        try {
            doRequest(req, resp);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
        try {
            doRequest(req, resp);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, URISyntaxException {

        try {
            String[] args = formatArgs(req);                    // formatting request to instantiate application
            RequestParser par = new RequestParser(args);        // parser object
            logger.info(par.toString());
            Charset utf8 = Charset.forName("utf-8");
            if(Debug.ON) System.out.println(Arrays.toString(args));


            try {
                cmd = new ProcessCmd().setLogger(logger).doProcess(par);  // do the heavy lifting
            } catch(Exception e){
               // logger.info(e.getMessage());
                logger.error("app throw "+ e.getClass().getSimpleName());
                if(e.getClass().getSimpleName().matches("CommunicationsException")){
                    logger.info("error 500: server error");
                    resp.sendError(500);
                } else {
                    logger.info("error 400: unable to find resource error");
                    resp.sendError(404);
                }
            }

            String method = req.getMethod();

            if(method.equals("GET")) { // GET Request
                byte[] respBodyBytes = cmd.outData.getBytes(utf8);  // get the output data

                resp.setStatus(200);
                resp.setContentLength(respBodyBytes.length);
                resp.setContentType(par.getHeaders().get("accept"));

                OutputStream os = resp.getOutputStream();
                os.write(respBodyBytes);

                os.close();

            } else if (method.equals("POST")){ // POST Request
                createdId = cmd.id; // create|update|delete id
                String redirect = getRedirectURL(req.getRequestURI());

                //resp.setHeader("Location", resp.encodeRedirectURL(req.getRequestURI()));
                logger.info(req.getRequestURI() +" -> "+ redirect);

                if(redirect.equals("")){
                    logger.info("500: Server error ");
                    resp.sendError(500);
                } else {
                    resp.setStatus(303);
                    logger.info("303: Server redirected ");
                    resp.setHeader("Location", resp.encodeRedirectURL(redirect));
                }
            }
        } catch (Exception e) {
            logger.error("Exception[doRequest] ("+req.getMethod()+"): " + e.getMessage());
            //e.printStackTrace();    // TODO: remove later (4 debugging)
        }

    }


    public String getRedirectURL(String reqURL) throws AppException {

        Pattern chkCOL  = Pattern.compile("/checklists$");                  // create a checklist
        Pattern chkDET  = Pattern.compile("/checklists/\\d+/tasks$");       // add a tag to a checklist
        Pattern tskStat = Pattern.compile("/checklists/\\d+/tasks/\\d+");   // change task status on a chklist
        Pattern tagCOL  = Pattern.compile("/tags$");                        // create a tag
        Pattern tagChk  = Pattern.compile("/checklists/\\d+/tags$");        // associate a tag to a chklist
        Pattern chkTag  = Pattern.compile("/tags/\\d+/checklists$");        // from all chcklists associated with a tag
        Pattern temNew  = Pattern.compile("/templates$");                   // create new template
        Pattern temDET  = Pattern.compile("/templates/\\d+/create$");       // create new checklist from template
        Pattern temTsk  = Pattern.compile("/templates/\\d+/tasks$");       // create new checklist from template

        if(chkCOL.matcher(reqURL).matches()) return "/checklists/"+createdId;               // goes to chklist detail
        if(chkDET.matcher(reqURL).matches()) return "/checklists/"+reqURL.split("/")[2];    // returns to the chklist detail
        if(tskStat.matcher(reqURL).matches()) return "/checklists/"+reqURL.split("/")[2];   // returns to the chklist detail
        if(tagCOL.matcher(reqURL).matches()) return "/tags/"+createdId;                     // goes to the tag detail
        if(tagChk.matcher(reqURL).matches()) return "/checklists/"+reqURL.split("/")[2];    // returns to the chklist detail
        if(chkTag.matcher(reqURL).matches()) return "/checklists/"+createdId;
        if(temNew.matcher(reqURL).matches()) return "/templates/"+createdId;
        if(temDET.matcher(reqURL).matches()) return "/checklists/"+createdId;               // goes to chklist detail when created from template
        if(temTsk.matcher(reqURL).matches()) return "/templates/"+reqURL.split("/")[2];               // goes to chklist detail when created from template


        logger.error("WebParser: error on getRedirectURL");
        return "";
    }

//    public HttpResponse doHandleErrors(){
//
//    }


    public String[] formatArgs(HttpServletRequest req) throws ServletException, IOException, URISyntaxException{
        LinkedList<String> out = new LinkedList<>();
        URI reqUri = new URI(req.getRequestURI());

        // curl -X GET -G localhost:8080/xpto -H "accept: Application/json" -d id=3 -d color=red

        out.add(req.getMethod());                                       // method
        String webpath = reqUri.getPath().equals("") ? "/" : reqUri.getPath();  // get root doesnt come with an slash
        out.add(webpath);                                               // path
        //if(req.getHeader("Accept").matches(".*\\*\\/\\*.*")){           // headers
        if(req.getHeader("Accept").matches(".*text\\/html.*")){
                if(Debug.ON) System.out.println("> matched");
            out.add("accept:text/html");    // <----------------------------------------------------------------------
            //out.add("accept:"+req.getHeader("Accept").toLowerCase());
        }else if(req.getHeader("Accept").matches(".*application\\/json.*")) {
            if(Debug.ON) System.out.println("> generic");
            //out.add("accept:"+req.getHeader("Accept").toLowerCase());
            out.add("accept:application/json");
        }else if(req.getHeader("Accept").matches(".*text\\/plain.*")){
            out.add("accept:text/plain");
        }

        Map<String, String[]> webParams = req.getParameterMap();        // arguments
        HashMap<String,String> params = new HashMap<>();
        webParams.entrySet().stream().forEach(x -> params.put(x.getKey(), String.join(",",x.getValue())));
        params.values().removeAll(Collections.singleton(""));   // remove empty params

        if(params.size() != 0){
            Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("=");
            out.add(mapJoiner.join(params));
        }

        //out.forEach(System.out::println);
        //System.out.println(out.toString());
        return out.toArray(new String[out.size()]);
    }

    public void setLogger(Logger logger){
        this.logger = logger;
    }

    @Override
    public String toString() {
        return "WebParser";
    }
}
