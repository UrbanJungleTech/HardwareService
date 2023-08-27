package urbanjungletech.hardwareservice.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @Type(value = LoggingAction.class, name = "loggingAction"),
        @Type(value = HardwareStateChangeAction.class, name = "hardwareStateChangeAction")
})
public abstract class Action {
    private Long id;
    private String type;
    private Long sensorReadingAlertId;

    public Action(String type) {
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

    public Long getSensorReadingAlertId() {
        return sensorReadingAlertId;
    }

    public void setSensorReadingAlertId(Long sensorReadingAlertId) {
        this.sensorReadingAlertId = sensorReadingAlertId;
    }
}
