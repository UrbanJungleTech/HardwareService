package urbanjungletech.hardwareservice.model.alert.condition;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
public abstract class AlertCondition {
    protected Long id;
    protected Long alertId;
    protected String type;


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    protected boolean active;

    public AlertCondition() {
        this.type = this.getClass().getSimpleName();
        this.active = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlertCondition)) return false;

        AlertCondition that = (AlertCondition) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getAlertId() != null ? !getAlertId().equals(that.getAlertId()) : that.getAlertId() != null) return false;
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 31 * result + (getType() != null ? getType().hashCode() : 0);
        result += 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
