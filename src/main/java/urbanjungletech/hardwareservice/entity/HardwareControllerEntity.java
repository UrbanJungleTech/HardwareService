package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    private String type;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "hardwareController", cascade = CascadeType.REMOVE)
    private List<HardwareEntity> hardware;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "hardwareController", cascade = CascadeType.ALL)
    private List<SensorEntity> Sensors;

    @ManyToOne
    private HardwareControllerGroupEntity controllerGroup;

    private String service;

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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HardwareControllerGroupEntity getControllerGroup() {
        return controllerGroup;
    }

    public void setControllerGroup(HardwareControllerGroupEntity controllerGroup) {
        this.controllerGroup = controllerGroup;
    }
}
