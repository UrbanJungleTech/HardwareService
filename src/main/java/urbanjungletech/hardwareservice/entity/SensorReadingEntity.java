package urbanjungletech.hardwareservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="SensorReading")
public class SensorReadingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double reading;
    private LocalDateTime readingTime;

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
}
