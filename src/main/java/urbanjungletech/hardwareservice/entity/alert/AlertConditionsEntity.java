package urbanjungletech.hardwareservice.entity.alert;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AlertConditionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private AlertEntity alert;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<AlertConditionEntity> activeConditions;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<AlertConditionEntity> inactiveConditions;
    @OneToMany(cascade = CascadeType.ALL)
    private List<AlertConditionEntity> conditions;

    public AlertConditionsEntity() {
        this.activeConditions = new HashSet<>();
        this.inactiveConditions = new HashSet<>();
        this.conditions = new ArrayList<>();
    }

    public List<AlertConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(List<AlertConditionEntity> conditions) {
        this.conditions = conditions;
    }

    public Set<AlertConditionEntity> getInactiveConditions() {
        return inactiveConditions;
    }

    public void setInactiveConditions(Set<AlertConditionEntity> inactiveConditions) {
        this.inactiveConditions = inactiveConditions;
    }

    public Set<AlertConditionEntity> getActiveConditions() {
        return activeConditions;
    }

    public void setActiveConditions(Set<AlertConditionEntity> activeConditions) {
        this.activeConditions = activeConditions;
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
