package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ---Helper class---
 * Provide the data given in /resources/config.properties
 */
public class ConfigDetails {
    private String identityId;
    private String userUniqueId;
    private String dataSetName;
    private String recordKey;
    private String recordValue;
    private int tokenExpirationTime;

    /**
     * Load the details from environment.properties
     * Initialize the class members
     */
    public ConfigDetails(){
        Properties prop = new Properties();
        try {
            final String f_environment = "../config.properties";
            InputStream inputStream = this.getClass().getResourceAsStream(f_environment);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get the properties
        identityId = prop.getProperty("identityId");
        userUniqueId = prop.getProperty("userUniqueId");
        dataSetName = prop.getProperty("dataSetName");
        recordKey = prop.getProperty("recordKey");
        recordValue = prop.getProperty("recordValue");
        tokenExpirationTime = Integer.parseInt(prop.getProperty("tokenExpirationTime"));
    }

    public String getIdentityId() {
        return identityId;
    }

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public String getDataSetName() {
        return dataSetName;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public String getRecordValue() {
        return recordValue;
    }

    public int getTokenExpirationTime() {
        return tokenExpirationTime;
    }
}
