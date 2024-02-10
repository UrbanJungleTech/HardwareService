package urbanjungletech.hardwareservice.model.credentials;

public class TokenCredentials extends Credentials{
    private String tokenValue;

    public TokenCredentials(){
        super();
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }
}
