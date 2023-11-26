package urbanjungletech.hardwareservice.entity.alert.condition;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AlertConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "alert_condition_id")
    private AlertEntity alert;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AlertEntity getAlert() {
        return alert;
    }

    public void setAlert(AlertEntity alert) {
        this.alert = alert;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
