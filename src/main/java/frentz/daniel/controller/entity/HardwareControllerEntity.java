package frentz.daniel.controller.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HardwareControllerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String serialNumber;
    @OneToMany(mappedBy = "hardwareController", cascade = {CascadeType.PERSIST})
    private List<HardwareEntity> hardware;
    @OneToMany(mappedBy = "hardwareController", cascade = {CascadeType.PERSIST})
    private List<SensorEntity> sensors;


    public HardwareControllerEntity(){
        this.hardware = new ArrayList<>();
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

    public List<HardwareEntity> getHardware() {
        return hardware;
    }

    public void setHardware(List<HardwareEntity> hardware) {
        this.hardware = hardware;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<SensorEntity> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorEntity> sensors) {
        this.sensors = sensors;
    }
}
