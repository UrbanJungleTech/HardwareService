package urbanjungletech.hardwareservice.model;

import java.util.ArrayList;
import java.util.List;

public class HardwareController {
    private Long id;
    private String name;
    private String type;
    private List<Hardware> hardware;
    private List<Sensor> sensors;
    private String serialNumber;
    private Long hardwareControllerGroupId;

    public HardwareController(){
        this.sensors = new ArrayList<>();
        this.hardware = new ArrayList<>();
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
}
