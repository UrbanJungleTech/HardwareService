package urbanjungletech.hardwareservice.config.digitaltwin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "digitaltwins")
public class DigitalTwinsConfiguration {
    private String clientId;
    private String tenantId;
    private String clientSecret;
    private String digitalTwinsUri;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getDigitalTwinsUri() {
        return digitalTwinsUri;
    }

    public void setDigitalTwinsUri(String digitalTwinsUri) {
        this.digitalTwinsUri = digitalTwinsUri;
    }
}
