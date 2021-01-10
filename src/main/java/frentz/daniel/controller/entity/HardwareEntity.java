package frentz.daniel.controller.entity;

import frentz.daniel.controllerclient.model.HardwareCategory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long port;
    private String name;
    private HardwareCategory hardwareCategory;
    @Embedded
    private HardwareStateEntity desiredState;
    @Embedded
    private HardwareStateEntity currentState;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    @OneToMany(mappedBy = "hardware")
    private List<TimerEntity> timers;

    public HardwareEntity(){
        this.timers = new ArrayList<TimerEntity>();
    }

    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
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

    public HardwareControllerEntity getHardwareController() {
        return hardwareController;
    }

    public void setHardwareController(HardwareControllerEntity hardwareController) {
        this.hardwareController = hardwareController;
    }


    public HardwareStateEntity getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(HardwareStateEntity desiredState) {
        this.desiredState = desiredState;
    }

    public HardwareStateEntity getCurrentState() {
        return currentState;
    }

    public void setCurrentState(HardwareStateEntity currentState) {
        this.currentState = currentState;
    }

    public HardwareCategory getHardwareCategory() {
        return hardwareCategory;
    }

    public void setHardwareCategory(HardwareCategory hardwareCategory) {
        this.hardwareCategory = hardwareCategory;
    }

    public List<TimerEntity> getTimers() {
        return timers;
    }

    public void setTimers(List<TimerEntity> timers) {
        this.timers = timers;
    }
}
