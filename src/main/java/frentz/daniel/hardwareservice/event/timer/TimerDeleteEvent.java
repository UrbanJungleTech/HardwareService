package frentz.daniel.hardwareservice.event.timer;

public class TimerDeleteEvent {
    private long timerId;

    public TimerDeleteEvent(long timerId) {
        this.timerId = timerId;
    }

    public long getTimerId() {
        return timerId;
    }

    public void setTimerId(long timerId) {
        this.timerId = timerId;
    }
}
