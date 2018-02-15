package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ---Helper class---
 * Provide the data given in /resources/environment.properties
 */
public class EnvironmentDetails {
    private String region;
    private String identityPoolId;
    private String developerProviderName;

    /**
     * Load the details from environment.properties
     * Initialize the class members
     */
    public EnvironmentDetails(){
        Properties prop = new Properties();
        try {
            final String f_environment = "../environment.properties";
            InputStream inputStream = this.getClass().getResourceAsStream(f_environment);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get the properties
        region = prop.getProperty("region");
        identityPoolId = prop.getProperty("identityPoolId");
        developerProviderName = prop.getProperty("developerProviderName");
    }

    public String getRegion() {
        return region;
    }

    public String getDeveloperProviderName() {
        return developerProviderName;
    }

    public String getIdentityPoolId() {
        return identityPoolId;
    }
}
