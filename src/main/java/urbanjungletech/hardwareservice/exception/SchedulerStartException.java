package urbanjungletech.hardwareservice.exception;

public class SchedulerStartException extends RuntimeException{
    public SchedulerStartException(String scheduleType, long id){
        super("error starting schedule for " + scheduleType + "with id: " + id);
    }
}
