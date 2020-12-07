package pie.ilikepiefoo2.httpserver.http;

/**
 *
 * Main HTTP Request class
 *
 */
public class HttpRequest extends HttpMessage{

    // Used to store the resulting Http Request after parsing.
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    /**
     * Dummy Constructor
     */
    HttpRequest()
    {

    }

    /**
     * Used to return the method of the request
     * @return HttpMethod
     */
    public HttpMethod getMethod()
    {
        return method;
    }

    /**
     * Used to mutate the method.
     * @param methodName
     * if the method is not implemented already.
     * @throws HttpParsingException
     */
    void setMethod(String methodName) throws HttpParsingException
    {
        for(HttpMethod method : HttpMethod.values())
        {
            if(methodName.equals(method.name()))
            {
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }
}
