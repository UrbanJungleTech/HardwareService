package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.addition.ScheduledSensorReadingAdditionService;
import frentz.daniel.hardwareservice.addition.SensorAdditionService;
import frentz.daniel.hardwareservice.dao.SensorReadingDAO;
import frentz.daniel.hardwareservice.service.SensorService;
import frentz.daniel.hardwareservice.client.model.ScheduledSensorReading;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.client.model.SensorReading;
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
    private final HardwareControllerAdditionService hardwareControllerAdditionService;

    public SensorEndpoint(ScheduledSensorReadingAdditionService scheduledSensorReadingAdditionService,
                          SensorService sensorService,
                          SensorReadingDAO sensorReadingDAO,
                          SensorAdditionService sensorAdditionService,
                          HardwareControllerAdditionService hardwareControllerAdditionService){
        this.sensorService = sensorService;
        this.sensorReadingDAO = sensorReadingDAO;
        this.scheduledSensorReadingAdditionService = scheduledSensorReadingAdditionService;
        this.sensorAdditionService = sensorAdditionService;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable("sensorId") long sensorId){
        Sensor result = this.sensorService.getSensor(sensorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sensorId}/reading")
    public ResponseEntity<SensorReading> readSensor(@PathVariable("sensorId") long sensorId){
        SensorReading result = this.sensorService.readSensor(sensorId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sensorId}/readings/")
    public ResponseEntity<List<SensorReading>> getReadings(@PathVariable("sensorId") long sensorId,
                                                           @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH-mm-ss") LocalDateTime startDate,
                                                           @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH-mm-ss") LocalDateTime endDate){
        List<SensorReading> result = this.sensorReadingDAO.getReadings(sensorId, startDate, endDate);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{sensorId}/scheduledReading/")
    public ResponseEntity<ScheduledSensorReading> createScheduledSensorReading(@PathVariable("sensorId") long sensorId,
                                                                               @RequestBody ScheduledSensorReading scheduledSensorReading){
        ScheduledSensorReading result = this.sensorAdditionService.addScheduledReading(sensorId, scheduledSensorReading);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{sensorId}/scheduledReading/{scheduledReadingId}")
    public ResponseEntity deleteScheduledReading(@PathVariable("scheduledReadingId") long scheduledReadingId){
        this.scheduledSensorReadingAdditionService.delete(scheduledReadingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/")
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor,
                                            @PathVariable("hardwareControllerId") long hardwareControllerId){
        Sensor result = this.hardwareControllerAdditionService.addSensor(hardwareControllerId, sensor);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{sensorId}")
    public ResponseEntity deleteSensor(@PathVariable("sensorId") long sensorId){
        this.sensorAdditionService.delete(sensorId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{sensorId}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable ("sensorId") long sensorId, @RequestBody Sensor sensor){
        Sensor result = this.sensorAdditionService.update(sensorId, sensor);
        return ResponseEntity.ok(result);
    }
}