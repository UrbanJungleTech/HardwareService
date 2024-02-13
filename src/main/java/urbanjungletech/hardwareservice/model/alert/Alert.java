package urbanjungletech.hardwareservice.model.alert;

import urbanjungletech.hardwareservice.model.alert.action.AlertAction;

import java.util.ArrayList;
import java.util.List;

public class Alert {
    private Long id;
    private String name;
    private String description;
    private AlertConditions conditions;
    private List<AlertAction> alertActions;

    public Alert() {
        this.alertActions = new ArrayList<>();
        this.conditions = new AlertConditions();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AlertConditions getConditions() {
        return conditions;
    }

    public void setConditions(AlertConditions conditions) {
        this.conditions = conditions;
    }
}
