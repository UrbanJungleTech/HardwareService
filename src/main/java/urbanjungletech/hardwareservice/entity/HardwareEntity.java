package urbanjungletech.hardwareservice.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Hardware")
public class HardwareEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String port;
    private String name;
    private String hardwareCategory;
    private String offState;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> possibleStates;
    @ManyToOne
//    @Transient
    private HardwareEntity backup;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "desired_hardwarestate_id")
    private HardwareStateEntity desiredState;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "current_hardwarestate_id")
    private HardwareStateEntity currentState;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "hardware_id")
    private HardwareControllerEntity hardwareController;
    @OneToMany(mappedBy = "hardware", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<TimerEntity> timers;
    @ElementCollection()
    @CollectionTable(name = "metadata",
            joinColumns = {@JoinColumn(name = "hardware_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "metadata_data")
    @Column(name = "metadata_value")
    @Fetch(FetchMode.SUBSELECT)
    private Map<String, String> metadata;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "hardware_configuration",
            joinColumns = {@JoinColumn(name = "hardware_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "hardware_configuration_data")
    @Column(name = "configuration_value")
    @BatchSize(size = 20)
    private Map<String, String> configuration;

    public HardwareEntity(){
        this.timers = new ArrayList<TimerEntity>();
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

    public HardwareEntity getBackup() {
        return backup;
    }

    public void setBackup(HardwareEntity backup) {
        this.backup = backup;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOffState() {
        return offState;
    }

    public void setOffState(String offState) {
        this.offState = offState;
    }

    public List<String> getPossibleStates() {
        return possibleStates;
    }

    public void setPossibleStates(List<String> possibleStates) {
        this.possibleStates = possibleStates;
    }
}
