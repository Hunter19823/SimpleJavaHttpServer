package pie.ilikepiefoo2.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


/**
 *
 * Worker Thread Class
 *
 * Handles the processing of all requests.
 */
public class HTTPConnectionWorkerThread extends Thread {
    // A logger for logging anything related to this class.
    private final static Logger LOGGER = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);

    //Fields
    private Socket socket;

    // Constructor
    public HTTPConnectionWorkerThread(Socket socket)
    {
        this.socket = socket;
    }

    // Main Method
    @Override
    public void run()
    {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Input and Output streams for the socket.
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();


            // The message being sent through the output stream.
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This Page was served using my simple java HTTP Server.</h1></body></html>";

            // The Carriage Return Line feed
            final String CRLF = "\n\r"; // 13, 10 ACII

            // The Complete Server Response
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // Status Line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF + // HEADER
                            CRLF +
                            html +
                            CRLF + CRLF;


            // Send server response as array of bytes.
            outputStream.write(response.getBytes());
            LOGGER.info("Connection Processing Finished...");
        } catch (IOException e) {
            // Log any communication errors.
            LOGGER.error("Problem with communication",e);
        }finally {
            // Close any streams that have been opened.
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) { }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) { }
            }
            // Close the socket itself
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) { }
            }
        }
    }
}
