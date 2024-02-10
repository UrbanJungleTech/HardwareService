package urbanjungletech.hardwareservice.model.connectiondetails;

import urbanjungletech.hardwareservice.model.credentials.TokenCredentials;

public class WeatherConnectionDetails extends ConnectionDetails{
    private String url;
    private TokenCredentials credentials;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TokenCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(TokenCredentials credentials) {
        this.credentials = credentials;
    }
}
