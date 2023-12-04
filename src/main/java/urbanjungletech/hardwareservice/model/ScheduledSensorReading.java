package urbanjungletech.hardwareservice.model;

import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.ArrayList;
import java.util.List;

public class ScheduledSensorReading {
    private Long id;
    private Long sensorId;
    private String cronString;
    private List<SensorReadingRouter> routers;

    public ScheduledSensorReading(){
        this.routers = new ArrayList<>();
    }

    public String getCronString() {
        return cronString;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public List<SensorReadingRouter> getRouters() {
        return routers;
    }

    public void setRouters(List<SensorReadingRouter> routers) {
        this.routers = routers;
    }
}
