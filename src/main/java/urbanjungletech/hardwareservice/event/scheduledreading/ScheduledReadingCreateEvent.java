package urbanjungletech.hardwareservice.event.scheduledreading;

public class ScheduledReadingCreateEvent {
    private long scheduledReadingId;

    public ScheduledReadingCreateEvent(long scheduledReadingId){
        this.scheduledReadingId = scheduledReadingId;
    }
    public long getScheduledReadingId() {
        return scheduledReadingId;
    }

    public void setScheduledReadingId(long scheduledReadingId) {
        this.scheduledReadingId = scheduledReadingId;
    }
}
