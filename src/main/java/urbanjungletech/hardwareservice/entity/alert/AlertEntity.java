package urbanjungletech.hardwareservice.entity.alert;

import jakarta.persistence.*;
import urbanjungletech.hardwareservice.entity.alert.action.AlertActionEntity;
import urbanjungletech.hardwareservice.entity.alert.condition.AlertConditionEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "alert")
    private List<AlertActionEntity> actions;
    @OneToMany(mappedBy = "alert")
    private List<AlertConditionEntity> conditions;

    public AlertEntity() {
        this.actions = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    private Double threshold;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public List<AlertActionEntity> getActions() {
        return actions;
    }

    public void setActions(List<AlertActionEntity> actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AlertConditionEntity> getConditions() {
        return conditions;
    }

    public void setConditions(List<AlertConditionEntity> conditions) {
        this.conditions = conditions;
    }
}
