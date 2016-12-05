package pt.isel.ls.Helpers.WebServer;

import org.slf4j.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.Exceptions.WEBException;

public class WebServer {

    public Logger logger;
    public Server server;
    public ServletHandler handler;
    public WebParser parser;

    public WebServer(int port, Logger logger) {
        this.logger = logger;
        this.server = new Server(port);
        logger.info("setting up WebServer on Port: "+port);
        this.handler = new ServletHandler();
        server.setHandler(handler);
    }

    public WebServer WebParser(WebParser parser){
        this.parser = parser;
        parser.setLogger(logger);
        handler.addServletWithMapping(new ServletHolder(this.parser), "/*");
        logger.info("setting up servlets: " + parser.toString());
        return this;
    }

    public WebServer start() throws Exception {
        try {
            this.server.start();
            logger.info("starting server");
            this.server.join();
        } catch(Exception e) {
            throw new WEBException( "Houston we have a problem! "+ e.getMessage() );
        }
        return null;
    }

}
