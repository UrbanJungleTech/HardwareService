package urbanjungletech.hardwareservice.event.condition;

public class ConditionUpdateEvent {
    private Long conditionId;

    public ConditionUpdateEvent(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }
}
