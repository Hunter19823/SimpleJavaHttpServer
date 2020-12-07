package pie.ilikepiefoo2.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    // Logger for this class.
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    // Private fields.
    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    /**
     * Main Server Listening Thread
     *
     * @param port
     * @param webroot
     *
     * If the server socket fails to be created
     * @throws IOException
     */
    public ServerListenerThread(int port, String webroot) throws IOException
    {
        this.webroot = webroot;
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    // Entry point for this class.
    @Override
    public void run()
    {
        try {
            // While the server socket is not closed
            // and accepting requests
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                // Establish connection with client.
                Socket socket = serverSocket.accept();
                LOGGER.info(" * Connection Accepted: " + socket.getInetAddress());
                // Handle connection with client on separate thread.
                HTTPConnectionWorkerThread workerThread = new HTTPConnectionWorkerThread(socket);
                workerThread.start();
            }
        } catch (IOException e) {
            // Log any errors.
            LOGGER.error("Problem with setting socket",e);
        } finally {
            // Close the server socket when finally finished.
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }
    }
}
