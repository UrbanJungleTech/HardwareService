package urbanjungletech.hardwareservice.exception.exception;

public class SchedulerStartException extends RuntimeException{
    public SchedulerStartException(String scheduleType, long id){
        super("error starting schedule for " + scheduleType + "with id: " + id);
    }
}
