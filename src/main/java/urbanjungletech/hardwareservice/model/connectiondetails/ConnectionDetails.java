package urbanjungletech.hardwareservice.model.connectiondetails;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public abstract class ConnectionDetails {
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
