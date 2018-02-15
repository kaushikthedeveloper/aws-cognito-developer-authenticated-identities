package awsclients;

import com.amazonaws.services.cognitosync.AmazonCognitoSync;
import com.amazonaws.services.cognitosync.AmazonCognitoSyncClientBuilder;
import helpers.EnvironmentDetails;

/**
 * Builder class for AmazonCognitoSync
 */
public class CognitoSyncBuilder {
    private static AmazonCognitoSync cognitoSyncClient;

    private CognitoSyncBuilder() { }

    private static void initialize() {
        EnvironmentDetails environmentDetails = new EnvironmentDetails();
        cognitoSyncClient = AmazonCognitoSyncClientBuilder
                                .standard()
                                .withRegion(environmentDetails.getRegion())
                                .build();
    }

    public static AmazonCognitoSync getInstance() {
        if (cognitoSyncClient == null) {
            initialize();
        }
        return cognitoSyncClient;
    }

}
