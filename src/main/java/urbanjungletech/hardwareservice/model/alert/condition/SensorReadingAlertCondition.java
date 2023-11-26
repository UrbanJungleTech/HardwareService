package urbanjungletech.hardwareservice.model.alert.condition;

public class SensorReadingAlertCondition extends AlertCondition {
    private Long sensorId;
    private Long threshold;
    private ThresholdType thresholdType;

    public SensorReadingAlertCondition() {
        super("sensorReadingAlertCondition");
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    public ThresholdType getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(ThresholdType thresholdType) {
        this.thresholdType = thresholdType;
    }
}
