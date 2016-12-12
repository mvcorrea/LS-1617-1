package pt.isel.ls.Helpers.WebServer;


import com.google.common.base.Joiner;
import org.slf4j.Logger;
import pt.isel.ls.Exceptions.AppException;
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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WebParser extends HttpServlet {
    Logger logger;
    private CommandMatcher commands = new CommandMatcher();

    public WebParser() {

    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {


        if(req.getRequestURI().matches("/favicon.*")) return;

        logger.info("{} on '{}' with accept:'{}'", req.getMethod(), req.getRequestURI(), req.getHeader("Accept"));

        try {

            String[] args = formatArgs(req);
            RequestParser par = new RequestParser(args);
            logger.info(par.toString());
            Charset utf8 = Charset.forName("utf-8");

            // Normal usage
            ProcessCmd cmd  = new ProcessCmd().doProcess(par);
            byte[] respBodyBytes = cmd.outData.getBytes(utf8);

            // TODO: treat error codes

            resp.setStatus(200);
            resp.setContentLength(respBodyBytes.length);
            resp.setContentType(par.getHeaders().get("accept"));

            OutputStream os = resp.getOutputStream();
            os.write(respBodyBytes);

            os.close();

        } catch (URISyntaxException e) {
            logger.error("URISyntaxException: "+ e.getMessage());
            //e.printStackTrace();
        } catch (AppException e) {
            logger.error("AppException: "+ e.getMessage());
            //e.printStackTrace();
        } catch (SQLException e) {
            logger.error("SQLException: "+ e.getMessage());
            //e.printStackTrace();
        } catch (Exception e) {
            logger.error("Exception: "+ e.getMessage());
            //e.printStackTrace();
        }

    }


    public String[] formatArgs(HttpServletRequest req) throws ServletException, IOException, URISyntaxException{
        LinkedList<String> out = new LinkedList<>();
        URI reqUri = new URI(req.getRequestURI());

        // curl -X GET -G localhost:8080/xpto -H "accept: Application/json" -d id=3 -d color=red

        out.add(req.getMethod());                                       // methods
        out.add(reqUri.getPath());                                      // path
        if(req.getHeader("Accept").matches(".*\\*\\/\\*.*")){               // headers
                System.out.println("> matched");
            out.add("accept:text/html");
        }else{
            System.out.println("> generic");
            out.add("accept:"+req.getHeader("Accept").toLowerCase());
        }

        Map<String, String[]> webParams = req.getParameterMap();        // arguments
        HashMap<String,String> params = new HashMap<>();
        webParams.entrySet().stream().forEach(x -> params.put(x.getKey(), String.join(",",x.getValue())));

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
        return "WebParser{}";
    }
}
