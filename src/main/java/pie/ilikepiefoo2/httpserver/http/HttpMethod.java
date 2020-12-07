package pie.ilikepiefoo2.httpserver.http;

/**
 *
 * Enum of all the implemented HTTP methods.
 *
 */
public enum HttpMethod {
    GET, HEAD;
    public static int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        // Take the maximum length of all the method names
        // And set that as max length of method.
        for( HttpMethod method : values())
        {
            if(method.name().length() > tempMaxLength)
            {
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}
