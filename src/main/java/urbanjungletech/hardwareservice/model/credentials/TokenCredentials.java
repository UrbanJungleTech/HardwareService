package urbanjungletech.hardwareservice.model.credentials;

public class TokenCredentials extends Credentials{
    private String tokenValue;
    private String url;

    public TokenCredentials(){
        this.setType("tokenCredentials");
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
