package urbanjungletech.hardwareservice.event.timer;

public class TimerUpdateEvent {
    long timerId;

    public TimerUpdateEvent(long timerId){
        this.timerId = timerId;
    }

    public long getTimerId(){
        return timerId;
    }

    public void setTimerId(long timerId){
        this.timerId = timerId;
    }
}
