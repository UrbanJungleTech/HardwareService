package urbanjungletech.hardwareservice.model.sensorreadingrouter;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
public abstract class SensorReadingRouter {
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    private Long id;
    private String type;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
