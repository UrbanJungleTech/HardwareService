package urbanjungletech.hardwareservice.event.condition;

public class ConditionActiveEvent {
    private Long conditionId;

    public ConditionActiveEvent(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Long getConditionId() {
        return conditionId;
    }
}
