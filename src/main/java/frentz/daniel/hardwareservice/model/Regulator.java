package frentz.daniel.hardwareservice.model;

public class Regulator {
    private Long id;
    private long sensorId;
    private long minimumCorrectionHardwareId;
    private long maximumCorrectionHardwareId;
    private long minimumReading;
    private long maximumReading;
    private String checkInterval;
    private long correctionInterval;

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
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

    public String getCheckInterval() {
        return checkInterval;
    }

    public void setCheckInterval(String checkInterval) {
        this.checkInterval = checkInterval;
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
}
