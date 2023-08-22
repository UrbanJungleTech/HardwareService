package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Regulator")
public class RegulatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long minimumCorrectionHardwareId;
    private Long maximumCorrectionHardwareId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private HardwareControllerEntity hardwareControllerEntity;
    private String checkInterval;
    private Long minimumReading;
    private Long maximumReading;
    private Long correctionInterval;


    public String getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(String checkInterval) {
        this.checkInterval = checkInterval;
    }

    public long getMinimumReading() {
        return minimumReading;
    }

    public void setMinimumReading(long minimumReading) {
        this.minimumReading = minimumReading;
    }

    public long getMaximumReading() {
        return maximumReading;
    }

    public void setMaximumReading(long maximumReading) {
        this.maximumReading = maximumReading;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getMinimumCorrectionHardwareId() {
        return minimumCorrectionHardwareId;
    }

    public void setMinimumCorrectionHardwareId(long minimumCorrectionHardwareId) {
        this.minimumCorrectionHardwareId = minimumCorrectionHardwareId;
    }

    public long getMaximumCorrectionHardwareId() {
        return maximumCorrectionHardwareId;
    }

    public void setMaximumCorrectionHardwareId(long maximumCorrectionHardwareId) {
        this.maximumCorrectionHardwareId = maximumCorrectionHardwareId;
    }

    public long getCorrectionInterval() {
        return correctionInterval;
    }

    public void setCorrectionInterval(long correctionInterval) {
        this.correctionInterval = correctionInterval;
    }

    public HardwareControllerEntity getHardwareControllerEntity() {
        return hardwareControllerEntity;
    }

    public void setHardwareControllerEntity(HardwareControllerEntity hardwareControllerEntity) {
        this.hardwareControllerEntity = hardwareControllerEntity;
    }
}
