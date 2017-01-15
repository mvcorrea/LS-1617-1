package pt.isel.ls.Commands;

import org.apache.commons.lang.StringUtils;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.sql.Connection;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.isel.ls.Helpers.WebServer.WebParser;
import pt.isel.ls.Helpers.WebServer.WebServer;


public class CMD_ServerListen  implements CommandInterface {
    public static String pattern = "(LISTEN /)";
    public RequestParser request;
    private int DEF_PORT = 80;
    public WebServer srv;
    private int port;


    @Override
    public RequestParser getRequest() { return request; }


    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets","true");
        Logger logger = LoggerFactory.getLogger(CMD_ServerListen.class);
        logger.info("Starting main...");
        port = par.getParams() != null ? Integer.valueOf(par.getParams().get("port")) : DEF_PORT;
        this.srv = new WebServer(port, logger).WebParser(new WebParser()).start();
        logger.info("main ends.");
        return new CommandWrapper(this);
    }

    @Override
    public boolean validate(RequestParser par) throws AppException, java.text.ParseException {
        return false;
    }

    @Override
    public Object getData() {
        return "LISTEN / - Starts the HTTP server";
    }
}
