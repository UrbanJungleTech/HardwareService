package urbanjungletech.hardwareservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hardware {
    private Long id;
    private String port;
    private String name;
    private String type;
    private List<String> possibleStates;
    private String offState;
    private HardwareState desiredState;
    private HardwareState currentState;
    private Long hardwareControllerId;
    private List<Timer> timers;
    private Map<String, String> metadata;
    private Map<String, String> configuration;

    public Hardware(){
        this.metadata = new HashMap<>();
        this.timers = new ArrayList<>();
        this.configuration = new HashMap<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getHardwareControllerId() {
        return hardwareControllerId;
    }

    public void setHardwareControllerId(Long hardwareControllerId) {
        this.hardwareControllerId = hardwareControllerId;
    }

    public HardwareState getDesiredState() {
        return this.desiredState;
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

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public List<String> getPossibleStates() {
        return possibleStates;
    }

    public void setPossibleStates(List<String> possibleStates) {
        this.possibleStates = possibleStates;
    }

    public String getOffState() {
        return offState;
    }

    public void setOffState(String offState) {
        this.offState = offState;
    }
}
