package urbanjungletech.hardwareservice.service.action;

public interface ActionExecutionService<T> {
    void execute(T action);
}
