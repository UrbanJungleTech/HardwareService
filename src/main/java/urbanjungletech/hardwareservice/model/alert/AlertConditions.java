package urbanjungletech.hardwareservice.model.alert;

import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlertConditions {
    private Set<AlertCondition> activeConditions;
    private Set<AlertCondition> inactiveConditions;
    private List<AlertCondition> conditions;
    private Long alertId;

    public AlertConditions() {
        this.activeConditions = new HashSet<>();
        this.inactiveConditions = new HashSet<>();
        this.conditions = new ArrayList<>();
    }

    public Set<AlertCondition> getActiveConditions() {
        return activeConditions;
    }

    public void setActiveConditions(Set<AlertCondition> activeConditions) {
        this.activeConditions = activeConditions;
    }

    public Set<AlertCondition> getInactiveConditions() {
        return inactiveConditions;
    }

    public void setInactiveConditions(Set<AlertCondition> inactiveConditions) {
        this.inactiveConditions = inactiveConditions;
    }

    public List<AlertCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<AlertCondition> conditions) {
        this.conditions = conditions;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
}
