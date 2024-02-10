package urbanjungletech.hardwareservice.model.connectiondetails;

import urbanjungletech.hardwareservice.model.credentials.Credentials;

public class AzureConnectionDetails extends ConnectionDetails{
    private String url;
    private Credentials credentials;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
