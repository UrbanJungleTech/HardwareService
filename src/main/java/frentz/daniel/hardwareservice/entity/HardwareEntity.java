package frentz.daniel.hardwareservice.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Hardware")
public class HardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long port;
    private String name;
    private String hardwareCategory;
    @Embedded()
    private HardwareStateEntity desiredState;
    @Embedded()
    private HardwareStateEntity currentState;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    @OneToMany(mappedBy = "hardware", fetch = FetchType.EAGER)
    private List<TimerEntity> timers;
    @ElementCollection
    @CollectionTable(name = "metadata",
            joinColumns = {@JoinColumn(name = "hardware_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "metadata_data")
    @Column(name = "metadata_value")
    private Map<String, String> metadata;

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


    public List<TimerEntity> getTimers() {
        return timers;
    }

    public void setTimers(List<TimerEntity> timers) {
        this.timers = timers;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getHardwareCategory() {
        return hardwareCategory;
    }

    public void setHardwareCategory(String hardwareCategory) {
        this.hardwareCategory = hardwareCategory;
    }
}
