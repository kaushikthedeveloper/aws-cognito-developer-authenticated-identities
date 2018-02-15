import awsclients.CognitoSyncBuilder;
import com.amazonaws.services.cognitosync.AmazonCognitoSync;
import com.amazonaws.services.cognitosync.model.*;
import helpers.ConfigDetails;
import helpers.EnvironmentDetails;

/**
 * BackEnd <---> Cognito
 * <p>
 * To get DataSet (dataSetName) for specified User (identityId)
 * and fetch its records => {key:value} pairs
 * <p>
 * Pass the following to Cognito :
 * - Identity ID
 * - Identity Pool ID
 * - DataSet Name
 */
public class ShowRecord {
    private final EnvironmentDetails environmentDetails = new EnvironmentDetails();
    private final ConfigDetails configDetails = new ConfigDetails();

    private final String identityId = configDetails.getIdentityId();
    private final String identityPoolId = environmentDetails.getIdentityPoolId();
    private final String dataSetName = configDetails.getDataSetName();              //new DataSet to be created

    public ListRecordsResult showDataSetRecord() {
        AmazonCognitoSync amazonCognitoSyncClient = CognitoSyncBuilder.getInstance();
        return listRecordsForSessionToken(amazonCognitoSyncClient);
    }

    /**
     * List Records called with to be created DataSet name
     * -> to get the syncSessionToken
     *
     * @param amazonCognitoSyncClient : AmazonCognitoSync used
     * @return {JSON}
     */
    private ListRecordsResult listRecordsForSessionToken(AmazonCognitoSync amazonCognitoSyncClient) {
        ListRecordsRequest listRecordsRequest = new ListRecordsRequest()
                .withIdentityId(identityId)
                .withIdentityPoolId(identityPoolId)
                .withDatasetName(dataSetName);
        return amazonCognitoSyncClient.listRecords(listRecordsRequest);
    }

}
