package urbanjungletech.hardwareservice.model.hardware;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
public abstract class Hardware {
    private Long id;
    private String port;
    private String name;
    private String type;
    private List<String> possibleStates;
    private String serialNumber;
    private String offState;
    private HardwareState desiredState;
    private HardwareState currentState;
    private Long hardwareControllerId;
    private List<Timer> timers;

    public Hardware(){
        this.type = this.getClass().getSimpleName();
        this.timers = new ArrayList<>();
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
