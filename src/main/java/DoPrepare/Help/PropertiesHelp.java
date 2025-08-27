package DoPrepare.Help;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertiesHelp {

    private static Properties properties;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;

    // Get current project path
    static String projectPath = System.getProperty("user.dir") + "/";
    // Default path to config.properties file
    private static String propertiesFilePathRoot = "src/main/resources/PropertiesFile.properties";

    public static void setPropertiesFile() {
        properties = new Properties();
        try {
            // Initialize FileInputStream object
            fileIn = new FileInputStream(projectPath + propertiesFilePathRoot);
            // Load properties file
            properties.load(fileIn);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }

    // Get value from key in the loaded properties file
    public static String getPropValue(String KeyProp) {
        String value = null;
        try {
            // Get value from properties file
            value = properties.getProperty(KeyProp);
            System.out.println(value);
            return value;
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
        return value;
    }

    // Set value for a key in the loaded properties file
    public static synchronized void setPropValue(String KeyProp, String Value) {
        try {
            // Initialize FileOutputStream object
            fileOut = new FileOutputStream(projectPath + propertiesFilePathRoot);
            // Set value for the specified key
            properties.setProperty(KeyProp, Value);
            // Save key and value to properties file
            properties.store(fileOut, "Set new value in properties file");
            System.out.println("Set new value in file properties success.");
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println(exp.getCause());
            exp.printStackTrace();
        }
    }
}