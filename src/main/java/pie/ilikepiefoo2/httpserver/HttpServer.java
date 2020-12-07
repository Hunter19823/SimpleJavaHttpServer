package pie.ilikepiefoo2.httpserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pie.ilikepiefoo2.httpserver.config.Config;
import pie.ilikepiefoo2.httpserver.config.ConfigManager;
import pie.ilikepiefoo2.httpserver.core.ServerListenerThread;

import java.io.IOException;

/**
 *
 * Driver Class for the http server.
 *
 */
public class HttpServer {
    // Used to log all the actions made by this class.
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);


    // Driver Method
    public static void main(String[] args) {
        LOGGER.info("Server Starting... ");
        // Loads the config for this server.
        ConfigManager.getInstance().loadConfigFile("src/main/resources/http.json");
        Config conf = ConfigManager.getInstance().getCurrentConfig();
        // Log the relative info about the server.
        LOGGER.info("Using Port: "+conf.getPort());
        LOGGER.info("Using Webroot: "+conf.getWebroot());

        // Tries to create a new server thread and starts it.
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO handle
        }

        LOGGER.info("All Servers Finished");
    }
}
