import awsclients.CognitoIdentityBuilder;
import helpers.ConfigDetails;
import helpers.EnvironmentDetails;
import com.amazonaws.services.cognitoidentity.*;
import com.amazonaws.services.cognitoidentity.model.*;
import models.CognitoIdentityIdToken;

import java.util.HashMap;

/**
 * BackEnd <---> Cognito
 *
 * To get the :
 * - IdentityId
 * - Token
 *
 * Pass the following to Cognito :
 * - Identity Pool ID
 * - Developer Provider Name
 * - User Unique ID
 * - Token Expire Time (optional)
 */
public class OpenIdToken {

    private final EnvironmentDetails environmentDetails = new EnvironmentDetails();
    private final ConfigDetails configDetails = new ConfigDetails();

    private final String identityPoolId = environmentDetails.getIdentityPoolId();
    private final String developerProviderName = environmentDetails.getDeveloperProviderName();
    private final String userUniqueId = configDetails.getUserUniqueId();
    private final int tokenExpirationTime = configDetails.getTokenExpirationTime();

    public CognitoIdentityIdToken getOpenIdToken(){
        AmazonCognitoIdentity cognitoIdentityClient = CognitoIdentityBuilder.getInstance();
        GetOpenIdTokenForDeveloperIdentityRequest openIdRequest = setOpenIdRequest();
        GetOpenIdTokenForDeveloperIdentityResult openIdResult = getOpenIdResult(cognitoIdentityClient, openIdRequest);
        return getCognitoTokenIdObject(openIdResult);
    }
    /**
     * Build the OpenIdToken Request
     * Configure
     * @return OpenIdToken Request
     */
    private GetOpenIdTokenForDeveloperIdentityRequest setOpenIdRequest() {
        GetOpenIdTokenForDeveloperIdentityRequest openIdRequest =
                new GetOpenIdTokenForDeveloperIdentityRequest();

        openIdRequest.setIdentityPoolId(identityPoolId);
        // Key -> Developer Provider Name used when creating the identity pool
        // Value -> Unique identifier of the user in your backend
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(developerProviderName, userUniqueId);
        openIdRequest.setLogins(map);
        // seconds : custom expiration time for the token so you can cache it
        // maximum token duration possible : 24 hours.
        // default = 15 mins
        openIdRequest.setTokenDuration((long) (60 * 60 * tokenExpirationTime));

        return openIdRequest;
    }

    /**
     * Get the OpenIDToken object from Cognito
     * @param client        : CognitoClient built
     * @param openIdRequest : OpenID Request with configurations
     * @return resultIdentityId
     */
    private GetOpenIdTokenForDeveloperIdentityResult getOpenIdResult(AmazonCognitoIdentity client, GetOpenIdTokenForDeveloperIdentityRequest openIdRequest){
            return client.getOpenIdTokenForDeveloperIdentity(openIdRequest);
    }

    /**
     * Get the IdentityId and Token
     * @param resultOpenId : OpenIdToken object - contains the IdentityId and Token
     * @return CognitoIdentityIdToken
     */
    private CognitoIdentityIdToken getCognitoTokenIdObject(GetOpenIdTokenForDeveloperIdentityResult resultOpenId){
        String resultIdentityId = resultOpenId.getIdentityId();
        String resultToken = resultOpenId.getToken();
        return new CognitoIdentityIdToken(resultIdentityId, resultToken);
    }

}
