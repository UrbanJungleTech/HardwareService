package frentz.daniel.hardwareservice.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HardwareController")
public class HardwareControllerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String serialNumber;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "hardwareController", cascade = CascadeType.PERSIST)
    private List<HardwareEntity> hardware;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "hardwareController")
    private List<SensorEntity> Sensors;


    public HardwareControllerEntity(){
        this.hardware = new ArrayList<>();
        this.Sensors = new ArrayList<>();
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
        return Sensors;
    }

    public void setSensors(List<SensorEntity> Sensors) {
        this.Sensors = Sensors;
    }
}
