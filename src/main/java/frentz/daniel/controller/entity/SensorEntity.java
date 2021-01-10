package frentz.daniel.controller.entity;

import frentz.daniel.controllerclient.model.SensorType;

import javax.persistence.*;
import java.util.List;

@Entity
public class SensorEntity {
    @Id
    @GeneratedValue
    private long id;
    @ElementCollection
    private List<SensorType> sensorTypes;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    private String serialNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HardwareControllerEntity getHardwareController() {
        return hardwareController;
    }

    public void setHardwareController(HardwareControllerEntity hardwareController) {
        this.hardwareController = hardwareController;
    }

    public List<SensorType> getSensorTypes() {
        return sensorTypes;
    }

    public void setSensorType(List<SensorType> sensorTypes) {
        this.sensorTypes = sensorTypes;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
