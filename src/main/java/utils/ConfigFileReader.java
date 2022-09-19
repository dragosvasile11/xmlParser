package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/***
 * This class is created to read the config.properties file.
 */
public class ConfigFileReader {

    public static Properties properties;
    private static final String configPath = "/home/dragos/codecool/xmlParser/src/main/resources/config.properties";

    /***
     * This method is reading and saving the properties from the config files.
     * @throws IOException - if the file was not found on the specified path.
     */
    public static void readConfigFile() throws IOException {

        FileInputStream fileInputStream;

        try {
            fileInputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(ExceptionsMessages.FILE_NOT_FOUND_MESSAGE, e);
        }

        properties = new Properties();
        properties.load(fileInputStream);
    }
}
