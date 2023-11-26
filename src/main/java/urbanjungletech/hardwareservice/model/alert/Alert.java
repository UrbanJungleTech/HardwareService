package urbanjungletech.hardwareservice.model.alert;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.ArrayList;
import java.util.List;

public class Alert {
    private Long id;
    private String name;
    private String description;
    private List<AlertCondition> conditions;
    private List<AlertAction> alertActions;

    public Alert() {
        this.alertActions = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AlertCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<AlertCondition> conditions) {
        this.conditions = conditions;
    }

    public List<AlertAction> getActions() {
        return alertActions;
    }

    public void setActions(List<AlertAction> alertActions) {
        this.alertActions = alertActions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
