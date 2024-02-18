package urbanjungletech.hardwareservice.entity.hardware.mqtt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class MqttPinEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Boolean levelable;
    private Integer pin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLevelable() {
        return levelable;
    }

    public void setLevelable(Boolean levelable) {
        this.levelable = levelable;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}
