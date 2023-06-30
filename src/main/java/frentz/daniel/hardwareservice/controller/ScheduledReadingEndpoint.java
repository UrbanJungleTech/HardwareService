package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduledreading")
public class ScheduledReadingEndpoint {

    private ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;

    public ScheduledReadingEndpoint(ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService){
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
    }
    @DeleteMapping("/scheduledReading/{scheduledReadingId}")
    public ResponseEntity deleteScheduledReadingById(@PathVariable("scheduledReadingId") long scheduledReadingId){
        this.scheduledSensorReadingAdditionService.delete(scheduledReadingId);
        return ResponseEntity.noContent().build();
    }
}
