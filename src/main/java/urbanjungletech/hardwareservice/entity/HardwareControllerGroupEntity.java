package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class HardwareControllerGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "controllerGroup")
    private List<HardwareControllerEntity> hardwareControllers;
    private String name;

    public HardwareControllerGroupEntity() {
        this.hardwareControllers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HardwareControllerEntity> getHardwareControllers() {
        return hardwareControllers;
    }

    public void setHardwareControllers(List<HardwareControllerEntity> hardwareControllers) {
        this.hardwareControllers = hardwareControllers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
