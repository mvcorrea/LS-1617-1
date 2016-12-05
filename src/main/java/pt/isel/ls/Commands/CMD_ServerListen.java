package pt.isel.ls.Commands;

import org.json.simple.parser.ParseException;
import pt.isel.ls.Exceptions.AppException;
import pt.isel.ls.Exceptions.DBException;
import pt.isel.ls.Helpers.CommandInterface;
import pt.isel.ls.Helpers.CommandWrapper;
import pt.isel.ls.Helpers.RequestParser;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import pt.isel.ls.Helpers.WebServer.WebParser;
import pt.isel.ls.Helpers.WebServer.WebServer;


public class CMD_ServerListen  implements CommandInterface {
    public static String pattern = "(LISTEN /)";
    public RequestParser request;
    private static final int DEF_PORT = 8080;
    public WebServer srv;

    @Override
    public RequestParser getRequest() { return request; }


    @Override
    public Pattern getPattern() { return Pattern.compile(pattern); }

    @Override
    public Object process(Connection con, RequestParser par) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.levelInBrackets","true");
        Logger logger = LoggerFactory.getLogger(CMD_ServerListen.class);
        logger.info("Starting main...");
        this.srv = new WebServer(DEF_PORT, logger).WebParser(new WebParser()).start();
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
