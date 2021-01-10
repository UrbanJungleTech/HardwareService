package frentz.daniel.controller.controller;

import frentz.daniel.controller.service.SensorService;
import frentz.daniel.controllerclient.model.SensorResult;
import frentz.daniel.controllerclient.model.SensorType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private SensorService sensorService;

    public SensorController(SensorService sensorService){
        this.sensorService = sensorService;
    }

    @GetMapping("/{sensorId}")
    public SensorResult getSensorReading(@PathVariable long sensorId, @RequestParam(name="sensorType") SensorType[] sensorType){
        return this.sensorService.readSensor(sensorId, sensorType);
    }
}
