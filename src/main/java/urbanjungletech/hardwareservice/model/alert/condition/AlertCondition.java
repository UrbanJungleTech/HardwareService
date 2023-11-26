package urbanjungletech.hardwareservice.model.alert.condition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;
import urbanjungletech.hardwareservice.model.alert.action.LoggingAlertAction;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SensorReadingAlertCondition.class, name = "sensorReadingAlertCondition"),
        @JsonSubTypes.Type(value = HardwareStateChangeAlertCondition.class, name = "hardwareStateChangeAlertCondition")
})
public class AlertCondition {
    protected Long id;
    protected Long alertId;
    protected String type;

    public AlertCondition(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
