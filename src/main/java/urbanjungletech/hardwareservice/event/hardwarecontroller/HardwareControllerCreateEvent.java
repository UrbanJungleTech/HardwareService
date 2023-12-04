package urbanjungletech.hardwareservice.event.hardwarecontroller;

public class HardwareControllerCreateEvent {
    private long id;

    public HardwareControllerCreateEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
