package pie.ilikepiefoo2.httpserver.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import pie.ilikepiefoo2.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * Config Manager for the server.
 *
 */
public class ConfigManager {
    private static ConfigManager myConfigManager;
    private static Config myCurrentConfig;

    /**
     * Dummy Constructor.
     */
    public ConfigManager() {
    }

    /**
     * Returns the current config manager
     * if none exist it creates one.
     * @return configManager
     */
    public static ConfigManager getInstance()
    {
        if(myConfigManager == null)
            myConfigManager = new ConfigManager();
        return myConfigManager;
    }

    /**
     * Used to load the configuration file by the path given.
     *
     * @param filepath
     * if the file cannot be found or
     * the file cannot be read or
     * the file is not in a proper JSON format or
     * the config object was not able to be create from the json.
     * @throws HttpConfigurationException
     */
    public void loadConfigFile(String filepath)
    {
        /*
            Try to open the file at filepath
            otherwise throw a
            generic config error.
         */
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filepath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        /*
            Attempt to read the file
            otherwise throw a
            generic config error
         */
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }
        } catch (IOException e)
        {
            throw new HttpConfigurationException(e);
        }

        /*
            Attempt to parse the resulting
            String as a Json Node.
            Otherwise throw a parsing error.
         */
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File", e);
        }
        /*
            Attempt to create a config object
            from the resulting Json Node.
            Otherwise throw an internal parsing
            error.
         */
        try {
            myCurrentConfig = Json.fromJson(conf, Config.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the Configuration File, internal", e);
        }

    }

    /**
     * Returns the Current Config
     *
     * if no current configuration is set.
     * @throws HttpConfigurationException
     */
    public Config getCurrentConfig()
    {
        if( myCurrentConfig == null)
            throw new HttpConfigurationException("No Current Configuration Set.");

        return myCurrentConfig;
        //return myConfigManager;
    }
}
