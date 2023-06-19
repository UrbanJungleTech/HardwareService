package frentz.daniel.hardwareservice.event.timer;

public class TimerCreateEvent {
    private long timerId;

    public TimerCreateEvent(long timerId) {
        this.timerId = timerId;
    }

    public long getTimerId() {
        return timerId;
    }

    public void setTimerId(long timerId) {
        this.timerId = timerId;
    }
}
