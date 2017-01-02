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

    public WebParser() {

    }


//    @Override
//    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//
//
//        if(req.getRequestURI().matches("/favicon.*")) return;
//
//        logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
//
//        try {
//
//            String[] args = formatArgs(req);
//            RequestParser par = new RequestParser(args);
//            logger.info(par.toString());
//            Charset utf8 = Charset.forName("utf-8");
//
//            // Normal usage
//            ProcessCmd cmd  = new ProcessCmd().doProcess(par);
//            byte[] respBodyBytes = cmd.outData.getBytes(utf8);
//
//            // TODO: treat error codes
//
//            resp.setStatus(200);
//            resp.setContentLength(respBodyBytes.length);
//            resp.setContentType(par.getHeaders().get("accept"));
//
//            OutputStream os = resp.getOutputStream();
//            os.write(respBodyBytes);
//
//            os.close();
//
//        } catch (URISyntaxException e) {
//            logger.error("GET URISyntaxException: "+ e.getMessage());
//            //e.printStackTrace();
//        } catch (AppException e) {
//            logger.error("GET AppException: "+ e.getMessage());
//            //e.printStackTrace();
//        } catch (SQLException e) {
//            logger.error("GET SQLException: "+ e.getMessage());
//            //e.printStackTrace();
//        } catch (Exception e) {
//            logger.error("GET Exception: "+ e.getMessage());
//            //e.printStackTrace();
//        }
//
//    }

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

//    @Override
//    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//
//        logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));
//
//        // curl -X POST -G localhost:8080/checklists -d name=chk2016_1 -d description=chk2016_1_desc -d dueDate=20160505\+1300
//
//        try {
//            String[] args = formatArgs(req);
//            System.out.println(">> "+Arrays.toString(args));
//            RequestParser par = new RequestParser(args);
//            logger.info(par.toString());
//            Charset utf8 = Charset.forName("utf-8");
//
//
//            // Normal usage
//            ProcessCmd cmd  = new ProcessCmd().doProcess(par);
//            byte[] respBodyBytes = cmd.outData.getBytes(utf8);
//
//
//            resp.setStatus(303);
//            resp.setHeader("Location", "http://www.google.com");
//
//            OutputStream os = resp.getOutputStream();
//            os.write(respBodyBytes);
//
//            os.close();
//
//        } catch (URISyntaxException e) {
//            logger.error("POST URISyntaxException: "+ e.getMessage());
//            //e.printStackTrace();
//        }  catch (Exception e) {
//            logger.error("POST Exception: " + e.getMessage());
//            //e.printStackTrace();
//        }
//
//    }


    public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, URISyntaxException {

        try {
            String[] args = formatArgs(req);                    // formatting request to instantiate application
            RequestParser par = new RequestParser(args);        // parser object
            logger.info(par.toString());
            Charset utf8 = Charset.forName("utf-8");

            ProcessCmd cmd  = new ProcessCmd().setLogger(logger).doProcess(par);  // do the heavy lifting

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
                int id = cmd.id; // create|update|delete id
                //resp.setHeader("Location", resp.encodeRedirectURL(req.getRequestURI()));
                logger.info(req.getRequestURI() +" -> "+getRedirectURL(req.getRequestURI()));

                resp.setStatus(303);
                resp.setHeader("Location", resp.encodeRedirectURL(getRedirectURL(req.getRequestURI())));
            }
        } catch (Exception e) {
            logger.error("Exception ("+req.getMethod()+"): " + e.getMessage());
            e.printStackTrace();    // TODO: remove latter (4 debugging)
        }

    }


    public String getRedirectURL(String reqURL) throws AppException {

        Pattern tskStat = Pattern.compile("/checklists/\\d+/tasks/\\d+");
        Pattern chkDET  = Pattern.compile("/checklists/\\d+/tasks$");
        Pattern chkCOL  = Pattern.compile("/checklists$");
        Pattern tagCOL  = Pattern.compile("/tags$");

        if(chkCOL.matcher(reqURL).matches()) return "/checklists";
        if(chkDET.matcher(reqURL).matches()) return "/checklists/"+reqURL.split("/")[2];
        if(tskStat.matcher(reqURL).matches()) return "/checklists/"+reqURL.split("/")[2];
        if(tagCOL.matcher(reqURL).matches()) return "/tags";
        logger.error("WebParser: error on getRedirectURL");
        throw new WEBException("error on getRedirectURL");
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
        if(req.getHeader("Accept").matches(".*\\*\\/\\*.*")){           // headers
            if(Debug.ON) System.out.println("> matched");
            out.add("accept:text/html");
        }else{
            if(Debug.ON) System.out.println("> generic");
            out.add("accept:"+req.getHeader("Accept").toLowerCase());
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
