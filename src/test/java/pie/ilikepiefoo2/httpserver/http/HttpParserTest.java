package pie.ilikepiefoo2.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * This class is used to test the HTTP parser.
 *
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass()
    {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = null;
        try {
            request = httpParser.parseHttpRequest(
                    generateValidTestCase()
            );
        } catch (HttpParsingException e)
        {
            fail(e);
        }

        assertEquals(request.getMethod(),HttpMethod.GET);
    }

    @Test
    void parseHttpRequestBadMethod1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName1()
            );
            fail();
        }catch(HttpParsingException e)
        {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestBadMethod2() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseMethodName2()
            );
            fail();
        }catch(HttpParsingException e)
        {
            assertEquals(e.getErrorCode(), HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseHttpRequestInvNumItems1() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestRequestLineInvNumItems()
            );
            fail();
        }catch(HttpParsingException e)
        {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpEmptyRequestLine() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestEmptyRequestLine()
            );
            fail();
        }catch(HttpParsingException e)
        {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseHttpRequestLineCRnoLF() {
        try {
            HttpRequest request = httpParser.parseHttpRequest(
                    generateBadTestCaseRequestLineOnlyCRnoLF()
            );
            fail();
        }catch(HttpParsingException e)
        {
            assertEquals(e.getErrorCode(), HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    private InputStream generateValidTestCase()
    {
        String CRLF = "\r\n";
        String rawData = "GET / HTTP/1.1" + CRLF +
                "Host: localhost:8080" + CRLF +
                "Connection: keep-alive" + CRLF +
                "Upgrade-Insecure-Requests: 1" + CRLF +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36" + CRLF +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" + CRLF +
                "Sec-Fetch-Site: none" + CRLF +
                "Sec-Fetch-Mode: navigate" + CRLF +
                "Sec-Fetch-User: ?1" + CRLF +
                "Sec-Fetch-Dest: document" + CRLF +
                "Accept-Encoding: gzip, deflate, br" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;

    }
    private InputStream generateBadTestCaseMethodName1()
    {
        String CRLF = "\r\n";
        String rawData = "GeT / HTTP/1.1" + CRLF +
                "Host: localhost:8080" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;

    }
    private InputStream generateBadTestCaseMethodName2()
    {
        String CRLF = "\r\n";
        String rawData = "GETTTT / HTTP/1.1" + CRLF +
                "Host: localhost:8080" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
    private InputStream generateBadTestRequestLineInvNumItems()
    {
        String CRLF = "\r\n";
        String rawData = "GET / AAAAAA HTTP/1.1" + CRLF +
                "Host: localhost:8080" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
    private InputStream generateBadTestEmptyRequestLine()
    {
        String CRLF = "\r\n";
        String rawData = "" + CRLF +
                "Host: localhost:8080" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
    private InputStream generateBadTestCaseRequestLineOnlyCRnoLF()
    {
        String CRLF = "\r\n";
        String rawData = "GET / HTTP/1.1\r"  + // <---- no \n
                "Host: localhost:8080" + CRLF +
                "Accept-Language: en-US,en;q=0.9" + CRLF +
                CRLF;
        InputStream inputStream = new ByteArrayInputStream(
                rawData.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}