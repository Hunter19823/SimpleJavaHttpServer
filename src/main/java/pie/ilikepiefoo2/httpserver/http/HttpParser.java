package pie.ilikepiefoo2.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * The class used to parse the httpRequest being sent
 * to the server.
 *
 */
public class HttpParser {
    // The logger for this class.
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // Constants
    private static final int SP = 0x20; // 32 (Space)
    private static final int CR = 0x0D; // 13 (\n)
    private static final int LF = 0x0A; // 10 (\r)


    /**
     * Parse an HTTP Request Stream
     * @param inputStream
     * @return an httpRequest object.
     * if there is an error parsing the request
     * @throws HttpParsingException
     */
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException
    {
        // Setup an input stream reader.
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        // Create a new Request object that is ready to be mutated.
        HttpRequest request = new HttpRequest();
        // Parse the requestLine
        try {
            parseRequestLine(reader, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO parse the headers
        parseHeaders(reader, request);
        // TODO parse the Body
        parseBody(reader, request);

        return request;
    }

    /**
     * Parses the request line of the httpRequest
     * @param reader
     * @param request
     * if the inputStreamReader has an IOException
     * @throws IOException
     * if the requestLine couldn't be parsed correctly.
     * @throws HttpParsingException
     */
    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException
    {
        // String Buffer
        StringBuilder processingDataBuffer = new StringBuilder();
        // Current byte being read.
        int _byte;

        // Used to keep track of what has been parsed so far.
        boolean methodParsed = false;
        boolean requestTarget = false;

        // No lexer, parse as data as a string.
        while((_byte = reader.read()) >= 0)
        {

            if(_byte == CR)
            {
                // Check ahead to see if the next character is a LF
                _byte = reader.read();
                if(_byte == LF)
                {
                    LOGGER.debug("Request Line VERSION to Process : {}", processingDataBuffer.toString());
                    // Either the method or request Target has not been parsed, throw bad request.
                    if(!methodParsed || !requestTarget)
                    {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    // Finish parsing the RequestLine
                    return;
                }
            }
            // If the bytes is a Separator character
            if(_byte == SP)
            {
                // TODO Process Previous Data
                // Check if we just parsed the method
                if(!methodParsed)
                {
                    LOGGER.debug("Request Line METHOD to Process : {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed=true;
                }
                // Check if we just parsed the requestTarget
                else if(!requestTarget)
                {
                    LOGGER.debug("Request Line REQ TARGET to Process : {}", processingDataBuffer.toString());
                    requestTarget=true;
                }
                // Throw an error if the request was bad.
                else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                // Clear buffer.
                processingDataBuffer.delete(0,processingDataBuffer.length());
            } else{
                // Add byte as a character to the string buffer.
                processingDataBuffer.append((char) _byte);
                // Check if the Method has any errors.
                if(!methodParsed)
                {
                    // Checks if the method is not yet implemented because it is too long.
                    if(processingDataBuffer.length() > HttpMethod.MAX_LENGTH)
                    {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request)
    {
        // TODO parse headers
    }

    private void parseBody(InputStreamReader reader, HttpRequest request)
    {
        // TODO parse body
    }



}
