package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import frentz.daniel.hardwareservice.addition.SensorAdditionService;
import frentz.daniel.hardwareservice.dao.SensorReadingDAO;
import frentz.daniel.hardwareservice.service.SensorService;
import frentz.daniel.hardwareservice.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.model.SensorReading;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorEndpoint {

    private final SensorService sensorService;
    private final ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService;
    private final SensorReadingDAO sensorReadingDAO;
    private final SensorAdditionService sensorAdditionService;

    public SensorEndpoint(ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                          SensorService sensorService,
                          SensorReadingDAO sensorReadingDAO,
                          SensorAdditionService sensorAdditionService){
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.sensorAdditionService = sensorAdditionService;
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable("sensorId") long sensorId){
        Sensor result = this.sensorService.getSensor(sensorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sensorId}/reading")
    public ResponseEntity<SensorReading> readSensorById(@PathVariable("sensorId") long sensorId){
        SensorReading result = this.sensorService.readSensor(sensorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sensorId}/readings")
    public ResponseEntity<List<SensorReading>> getReadingsBySensorId(@PathVariable("sensorId") long sensorId,
                                                           @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate){
        List<SensorReading> result = this.sensorReadingDAO.getReadings(sensorId, startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{sensorId}/scheduledReading/")
    public ResponseEntity<ScheduledSensorReading> addScheduledSensorReading(@PathVariable("sensorId") long sensorId,
                                                                               @RequestBody ScheduledSensorReading scheduledSensorReading){
        ScheduledSensorReading result = this.sensorAdditionService.addScheduledReading(sensorId, scheduledSensorReading);
        return ResponseEntity.created(null).body(result);
    }

    @DeleteMapping("/{sensorId}")
    public ResponseEntity deleteSensorById(@PathVariable("sensorId") long sensorId){
        this.sensorAdditionService.delete(sensorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable ("sensorId") long sensorId, @RequestBody Sensor sensor){
        Sensor result = this.sensorAdditionService.update(sensorId, sensor);
        return ResponseEntity.ok(result);
    }
}
