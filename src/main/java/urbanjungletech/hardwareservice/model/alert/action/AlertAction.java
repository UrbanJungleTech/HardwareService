package urbanjungletech.hardwareservice.model.alert.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = LoggingAlertAction.class, name = "loggingAlertAction"),
        @Type(value = HardwareStateChangeAlertAction.class, name = "hardwareStateChangeAction")
})
public abstract class AlertAction {
    private Long id;
    private String type;
    private Long alertId;

    public AlertAction(String type) {
        this.type = type;
    }

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

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
}
