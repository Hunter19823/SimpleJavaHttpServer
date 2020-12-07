package pie.ilikepiefoo2.httpserver.config;

/**
 * Exception Class for Server Configuration Errors
 */
public class HttpConfigurationException extends RuntimeException {

    /**
     * Dummy Constructor
     */
    public HttpConfigurationException()
    {
    }

    public HttpConfigurationException(String message)
    {
        super(message);
    }

    public HttpConfigurationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public HttpConfigurationException(Throwable cause)
    {
        super(cause);
    }
}
