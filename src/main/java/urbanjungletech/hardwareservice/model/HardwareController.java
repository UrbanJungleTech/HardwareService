package urbanjungletech.hardwareservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HardwareController {
    private Long id;
    private String name;
    private String type;
    private List<Hardware> hardware;
    private List<Sensor> sensors;
    private Long hardwareControllerGroupId;
    private Map<String, String> configuration;

    public HardwareController(){
        this.sensors = new ArrayList<>();
        this.hardware = new ArrayList<>();
        this.configuration = new HashMap<>();
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addHardware(Hardware hardware){
        this.hardware.add(hardware);
    }


    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getHardwareControllerGroupId() {
        return hardwareControllerGroupId;
    }

    public void setHardwareControllerGroupId(Long hardwareControllerGroupId) {
        this.hardwareControllerGroupId = hardwareControllerGroupId;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
}
