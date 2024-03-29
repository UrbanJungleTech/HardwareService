package urbanjungletech.hardwareservice.model.hardwarecontroller;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
public abstract class HardwareController {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @Valid
    private List<Hardware> hardware;
    @Valid
    private List<Sensor> sensors;
    private Long hardwareControllerGroupId;
    private String serialNumber;

    public HardwareController(){
        this.type = this.getClass().getSimpleName();
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
