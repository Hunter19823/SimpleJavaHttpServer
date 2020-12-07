package pie.ilikepiefoo2.httpserver.http;

/**
 *
 * Parsing Exceptions Class
 *
 */
public class HttpParsingException extends Exception {

    private final HttpStatusCode errorCode;

    public HttpParsingException(HttpStatusCode errorCode)
    {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public HttpStatusCode getErrorCode()
    {
        return errorCode;
    }
}
