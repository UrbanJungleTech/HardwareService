package urbanjungletech.hardwareservice.event.scheduledreading;

import urbanjungletech.hardwareservice.event.digitaltwins.CreateEvent;

public class ScheduledReadingCreateEvent implements CreateEvent {
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
