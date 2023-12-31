package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.service.query.ScheduledSensorReadingQueryService;

import java.util.List;

@RestController
@RequestMapping("/scheduledreading")
public class ScheduledReadingEndpoint {

    private ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;
    private ScheduledSensorReadingQueryService scheduledSensorReadingQueryService;

    public ScheduledReadingEndpoint(ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                                    ScheduledSensorReadingQueryService scheduledSensorReadingQueryService){
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.scheduledSensorReadingQueryService = scheduledSensorReadingQueryService;
    }
    @DeleteMapping("/{scheduledReadingId}")
    public ResponseEntity deleteScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId){
        this.scheduledSensorReadingAdditionService.delete(scheduledReadingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{scheduledReadingId}")
    public ResponseEntity<ScheduledSensorReading> getScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId){
        ScheduledSensorReading result = this.scheduledSensorReadingQueryService.getScheduledSensorReading(scheduledReadingId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<ScheduledSensorReading>> getAllScheduledReadings(){
        List<ScheduledSensorReading> result = this.scheduledSensorReadingQueryService.getScheduledSensorReadings();
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{scheduledReadingId}")
    public ResponseEntity<ScheduledSensorReading> updateScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId,
                                                                              @RequestBody ScheduledSensorReading scheduledSensorReading){
        ScheduledSensorReading result = this.scheduledSensorReadingAdditionService.update(scheduledReadingId, scheduledSensorReading);
        return ResponseEntity.ok().body(result);
    }
}
