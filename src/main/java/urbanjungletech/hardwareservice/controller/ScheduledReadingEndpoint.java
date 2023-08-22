package urbanjungletech.hardwareservice.controller;

import urbanjungletech.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReadingAlert;
import urbanjungletech.hardwareservice.service.ScheduledSensorReadingService;
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

    @PutMapping("/{scheduledReadingId}")
    public ResponseEntity<ScheduledSensorReading> updateScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId,
                                                                              @RequestBody ScheduledSensorReading scheduledSensorReading){
        ScheduledSensorReading result = this.scheduledSensorReadingAdditionService.update(scheduledReadingId, scheduledSensorReading);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/{scheduledReadingId}/sensorreadingalerts")
    public ResponseEntity<SensorReadingAlert> create(@RequestBody SensorReadingAlert sensorReadingAlert,
                                                     @PathVariable("scheduledReadingId") long scheduledReadingId){
        SensorReadingAlert result = this.scheduledSensorReadingAdditionService.addSensorReadingAlert(scheduledReadingId, sensorReadingAlert);
        return ResponseEntity.status(201).body(result);
    }
}
