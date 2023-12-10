package urbanjungletech.hardwareservice.entity.alert.condition;


import jakarta.persistence.Entity;
import urbanjungletech.hardwareservice.model.alert.condition.ThresholdType;

@Entity
public class SensorReadingAlertConditionEntity extends AlertConditionEntity{
    private Long sensorId;
    private Double threshold;
    private ThresholdType thresholdType;

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}
