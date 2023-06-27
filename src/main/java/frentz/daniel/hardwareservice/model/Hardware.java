package frentz.daniel.hardwareservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hardware {
    private Long id;
    private Long port;
    private String name;
    private String type;
    private HardwareState desiredState;
    private HardwareState currentState;
    private Long hardwareControllerId;
    private List<Timer> timers;
    private Map<String, String> metadata;

    public Hardware(){
        this.metadata = new HashMap<>();
        this.timers = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    public Long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(Long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }

    public HardwareState getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(HardwareState desiredState) {
        this.desiredState = desiredState;
    }

    public HardwareState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(HardwareState currentState) {
        this.currentState = currentState;
    }

    public List<Timer> getTimers() {
        return timers;
    }

    public void setTimers(List<Timer> timers) {
        this.timers = timers;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
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
}
