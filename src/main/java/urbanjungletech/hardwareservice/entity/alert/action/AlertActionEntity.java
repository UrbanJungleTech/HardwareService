package urbanjungletech.hardwareservice.entity.alert.action;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.alert.AlertEntity;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AlertActionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    @ManyToOne
    @JoinColumn(name = "alert_action_id")
    private AlertEntity alert;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AlertEntity getAlert() {
        return alert;
    }

    public void setAlert(AlertEntity alert) {
        this.alert = alert;
    }
}
