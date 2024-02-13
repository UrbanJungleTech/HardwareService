package urbanjungletech.hardwareservice.entity.alert;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AlertConditionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "alert_id", referencedColumnName = "id")
    private AlertEntity alert;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AlertConditionEntity> allConditions;

    public AlertConditionsEntity() {
        this.allConditions = new ArrayList<>();
    }

    public List<AlertConditionEntity> getAllConditions() {
        return allConditions;
    }

    public void setAllConditions(List<AlertConditionEntity> conditions) {
        this.allConditions = conditions;
    }

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
}
