import com.amazonaws.services.cognitosync.model.ListRecordsResult;
import models.CognitoIdentityIdToken;

/**
 * Runner for :
 * - OpenIdToken
 * - CreateRecord
 */
public class MainRunner {
    public static void main(String[] args){
        final OpenIdToken openIdToken = new OpenIdToken();
        CognitoIdentityIdToken cognitoIdentityIdToken = openIdToken.getOpenIdToken();
        System.out.println("Identity Id : " + cognitoIdentityIdToken.getIdentityId());
        System.out.println("OpenId Token : " + cognitoIdentityIdToken.getOpenIdToken());

        final ShowRecord showRecord = new ShowRecord();
        ListRecordsResult listRecordsResult = showRecord.showDataSetRecord();
        System.out.println("Record fetched : " + listRecordsResult.toString());

        final CreateRecord createRecord = new CreateRecord();
        createRecord.createDataSetRecord();
    }
}
