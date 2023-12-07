package urbanjungletech.hardwareservice.event.sensorreading;

public interface SpecificSensorReadingConditionEventHandler<Model>{
    void handleSensorReadingCreateEvent(Model model);
}
