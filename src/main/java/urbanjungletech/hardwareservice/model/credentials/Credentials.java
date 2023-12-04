package urbanjungletech.hardwareservice.model.credentials;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UsernamePasswordCredentials.class, name = "usernamePasswordCredentials"),
        @JsonSubTypes.Type(value = CertificateCredentials.class, name = "certificateCredentials"),
        @JsonSubTypes.Type(value = TokenCredentials.class, name = "tokenCredentials")
})
public class Credentials {
    private Long id;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
