package models;

/**
 * DataStructure
 * Holds the Identity ID and Token obtained from Cognito
 */
public class CognitoIdentityIdToken {
    private String identityId;
    private String openIdToken;

    public CognitoIdentityIdToken(String identityId, String token){
        this.identityId = identityId;
        this.openIdToken = token;
    }

    public String getIdentityId() {
        return identityId;
    }

    public String getOpenIdToken() {
        return openIdToken;
    }

    @Override
    public String toString() {
        return getIdentityId() + " : " + getOpenIdToken();
    }
}
