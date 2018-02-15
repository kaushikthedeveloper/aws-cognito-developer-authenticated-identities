package awsclients;

import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import helpers.EnvironmentDetails;

/**
 * Builder class for AmazonCognitoIdentity
 */
public class CognitoIdentityBuilder {
    private static AmazonCognitoIdentity cognitoIdentityClient;

    private CognitoIdentityBuilder() { }

    private static void initialize() {
        EnvironmentDetails environmentDetails = new EnvironmentDetails();
        cognitoIdentityClient = AmazonCognitoIdentityClientBuilder
                                .standard()
                                .withRegion(environmentDetails.getRegion())
                                .build();
    }

    public static AmazonCognitoIdentity getInstance() {
        if (cognitoIdentityClient == null) {
            initialize();
        }
        return cognitoIdentityClient;
    }
}
