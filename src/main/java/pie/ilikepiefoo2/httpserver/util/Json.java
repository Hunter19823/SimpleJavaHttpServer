package pie.ilikepiefoo2.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

/**
 *
 * JSON Utility Class
 *
 */
public class Json {
    // An ObjectMapper used to create the JSON.
    private static ObjectMapper myObjectMapper = defaultMapper();

    /**
     * Creates a default mapper configured to
     * fail on unknown properties.
     * @return ObjectMapper
     */
    public static ObjectMapper defaultMapper()
    {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return om;
    }

    /**
     * Parses a JSON tree from a string.
     * @param jsonSource
     * @return JsonNode
     * if there is issues reading the JSON
     * tree
     * @throws IOException
     */
    public static JsonNode parse(String jsonSource) throws IOException
    {
        return myObjectMapper.readTree(jsonSource);
    }


    /**
     * Creates an object from a JSON node.
     * @param node
     * @param clazz
     * @param <A>
     * @return Object
     * if the wrapper is unable to process
     * the request.
     * @throws JsonProcessingException
     */
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException
    {
        return myObjectMapper.treeToValue(node, clazz);
    }

    /**
     * Converts an object to a JSON tree.
     * @param obj
     * @return JsonNode
     */
    public static JsonNode toJson(Object obj)
    {
        return myObjectMapper.valueToTree(obj);
    }

    /**
     * Returns a JSON as a String
     * @param node
     * @return JSONString
     * if there is an issue processing the json
     * writer.
     * @throws JsonProcessingException
     */
    public static String stringify(JsonNode node) throws JsonProcessingException
    {
        return generateJson(node,false);
    }

    /**
     * Returns a JSON as a pretty
     * String
     * @param node
     * @return JSONString
     * if there is an issue processing the json
     * writer.
     * @throws JsonProcessingException
     */
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException
    {
        return generateJson(node,true);
    }

    /**
     * Generates a JSON in String form
     * @param o
     * Determines whether or not to pretty print
     * @param pretty
     * @return JSON as a String
     * if there is an issue processing the json
     * writer.
     * @throws JsonProcessingException
     */
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException
    {
        ObjectWriter myWriter = myObjectMapper.writer();
        if(pretty)
            myWriter = myWriter.with(SerializationFeature.INDENT_OUTPUT);
        return myWriter.writeValueAsString(o);
    }
}
