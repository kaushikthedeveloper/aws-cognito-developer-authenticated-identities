import awsclients.CognitoSyncBuilder;
import helpers.ConfigDetails;
import helpers.EnvironmentDetails;
import com.amazonaws.services.cognitosync.AmazonCognitoSync;
import com.amazonaws.services.cognitosync.model.*;
import org.json.JSONObject;

import java.util.Date;

/**
 * BackEnd <---> Cognito
 * <p>
 * To create DataSet for specified User (identityId)
 * and inside it, a record => {key:value} pair
 * <p>
 * Pass the following to Cognito :
 * - Identity ID
 * - Identity Pool ID
 * - DataSet Name
 * - Record Key
 * - Record Value
 */
public class CreateRecord {
    private final EnvironmentDetails environmentDetails = new EnvironmentDetails();
    private final ConfigDetails configDetails = new ConfigDetails();

    private final String identityId = configDetails.getIdentityId();
    private final String identityPoolId = environmentDetails.getIdentityPoolId();
    private final String dataSetName = configDetails.getDataSetName();              //new DataSet to be created
    private final String recordKey = configDetails.getRecordKey();
    private final String recordValue = configDetails.getRecordValue();

    public void createDataSetRecord() {
        AmazonCognitoSync amazonCognitoSyncClient = CognitoSyncBuilder.getInstance();
        String syncSessionToken = getSyncSessionToken(amazonCognitoSyncClient);
        RecordPatch recordPatch = buildRecordPatch();
        callUpdateRecords(amazonCognitoSyncClient, syncSessionToken, recordPatch);
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

    /**
     * After getting the JSON from listRecordsForSessionToken() , extract the syncSessionToken from it
     *
     * @param listRecordsResult : {JSON}
     * @return syncSessionToken
     */
    private String extractSyncSessionToken(ListRecordsResult listRecordsResult) {
        return new JSONObject(listRecordsResult).getString("syncSessionToken");
    }

    /**
     * Obtain the synSessionToken to be used when creating the Dataset
     *
     * @param amazonCognitoSyncClient : AmazonCognitoSync used
     * @return syncSessionToken
     */
    private String getSyncSessionToken(AmazonCognitoSync amazonCognitoSyncClient) {
        ListRecordsResult listRecordsResult = listRecordsForSessionToken(amazonCognitoSyncClient);
        return extractSyncSessionToken(listRecordsResult);
    }

    /**
     * Create a RecordPatch be used to create the record in the DataSet
     * provide the {key:value} pair
     *
     * @return RecordPatch
     */
    private RecordPatch buildRecordPatch() {
        return new RecordPatch()
                .withDeviceLastModifiedDate(new Date())
                .withOp(Operation.Replace)                                  // Replace || Remove
                .withSyncCount((long) 0)                                    // when unknown : 0
                .withKey(recordKey)                                         // Record to be created : Key
                .withValue(String.valueOf(recordValue));                    // Record to be created : Value
    }

    /**
     * At this point, the DataSet with the Record is created
     *
     * @param amazonCognitoSyncClient : AmazonCognitoSync used
     * @param syncSessionToken        : SessionToken for that period
     * @param recordPatch             : Defines the record to be created
     */
    private void callUpdateRecords(AmazonCognitoSync amazonCognitoSyncClient,
                                     String syncSessionToken, RecordPatch recordPatch) {
        UpdateRecordsRequest updateRecordsRequest = new UpdateRecordsRequest()
                .withIdentityId(identityId)
                .withIdentityPoolId(identityPoolId)
                .withDatasetName(dataSetName)
                .withSyncSessionToken(syncSessionToken)
                .withRecordPatches(recordPatch);
        amazonCognitoSyncClient.updateRecords(updateRecordsRequest);
    }


}
