package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SensorReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double reading;
    private LocalDateTime readingTime;
    private Long sensorId;

    public double getReading() {
        return reading;
    }

    public void setReading(double reading) {
        this.reading = reading;
    }

    public LocalDateTime getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(LocalDateTime readingTime) {
        this.readingTime = readingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }
}
