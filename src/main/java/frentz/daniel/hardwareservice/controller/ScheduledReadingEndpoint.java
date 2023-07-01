package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.service.ScheduledSensorReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduledreading")
public class ScheduledReadingEndpoint {

    private ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;
    private ScheduledSensorReadingService scheduledSensorReadingService;

    public ScheduledReadingEndpoint(ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                                    ScheduledSensorReadingService scheduledSensorReadingService){
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.scheduledSensorReadingService = scheduledSensorReadingService;
    }
    @DeleteMapping("/{scheduledReadingId}")
    public ResponseEntity deleteScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId){
        this.scheduledSensorReadingAdditionService.delete(scheduledReadingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{scheduledReadingId}")
    public ResponseEntity<ScheduledSensorReading> getScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId){
        ScheduledSensorReading result = this.scheduledSensorReadingService.getScheduledSensorReading(scheduledReadingId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<ScheduledSensorReading>> getAllScheduledReadings(){
        List<ScheduledSensorReading> result = this.scheduledSensorReadingService.getScheduledSensorReadings();
        return ResponseEntity.ok().body(result);
    }
}
