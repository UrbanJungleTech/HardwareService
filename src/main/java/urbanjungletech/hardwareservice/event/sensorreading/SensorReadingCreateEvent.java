package urbanjungletech.hardwareservice.event.sensorreading;

public class SensorReadingCreateEvent {
    private Long id;

    public SensorReadingCreateEvent(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
