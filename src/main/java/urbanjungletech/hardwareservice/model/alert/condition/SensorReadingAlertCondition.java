package urbanjungletech.hardwareservice.model.alert.condition;

public class SensorReadingAlertCondition extends AlertCondition {
    private Long sensorId;
    private Double threshold;
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
