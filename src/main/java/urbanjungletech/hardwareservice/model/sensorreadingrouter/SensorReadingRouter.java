package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DatabaseSensorReadingRouter.class, name = "databaseSensorReadingRouter"),
        @JsonSubTypes.Type(value = KafkaSensorReadingRouter.class, name = "kafkaSensorReadingRouter"),
        @JsonSubTypes.Type(value = BasicDatabaseSensorReadingRouter.class, name = "basicDatabaseSensorReadingRouter"),
        @JsonSubTypes.Type(value = AzureQueueSensorReadingRouter.class, name = "azureQueueSensorReadingRouter")
})
public class SensorReadingRouter {

    public SensorReadingRouter(String type) {
        this.type = type;
    }

    private Long ScheduledSensorReadingId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Long id;
    protected String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getScheduledSensorReadingId() {
        return ScheduledSensorReadingId;
    }

    public void setScheduledSensorReadingId(Long scheduledSensorReadingId) {
        ScheduledSensorReadingId = scheduledSensorReadingId;
    }
}
