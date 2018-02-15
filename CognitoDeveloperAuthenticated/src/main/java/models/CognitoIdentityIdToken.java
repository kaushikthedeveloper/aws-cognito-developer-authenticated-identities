package models;

/**
 * DataStructure
 * Holds the Identity ID and Token obtained from Cognito
 */
public class CognitoIdentityIdToken {
    private String identityId;
    private String token;

    public CognitoIdentityIdToken(String identityId, String token){
        this.identityId = identityId;
        this.token = token;
    }

    String getIdentityId() {
        return identityId;
    }

    String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return getIdentityId() + " : " + getToken();
    }
}
