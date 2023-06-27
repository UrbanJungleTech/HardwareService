package frentz.daniel.hardwareservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import frentz.daniel.hardwareservice.service.HardwareControllerSubscriptionService;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.Sensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping(value = "/hardwarecontroller", produces = "application/json")
public class HardwareControllerEndpoint {

    private final Logger logger = LoggerFactory.getLogger(HardwareControllerEndpoint.class);
    private final ObjectMapper objectMapper;
    private final HardwareControllerAdditionService hardwareControllerAdditionService;
    private final HardwareControllerSubscriptionService hardwareControllerSubscriptionService;
    private final HardwareControllerService hardwareControllerService;

    public HardwareControllerEndpoint(ObjectMapper objectMapper,
                                      HardwareControllerAdditionService hardwareControllerAdditionService,
                                      HardwareControllerSubscriptionService hardwareControllerSubscriptionService,
                                      HardwareControllerService hardwareControllerService){
        this.objectMapper = objectMapper;
        this.hardwareControllerService = hardwareControllerService;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
        this.hardwareControllerSubscriptionService = hardwareControllerSubscriptionService;
    }

    @PostMapping("/")
    public ResponseEntity<HardwareController> create(@RequestBody HardwareController hardwareController) throws JsonProcessingException {
        HardwareController result = this.hardwareControllerAdditionService.create(hardwareController);
        return ResponseEntity.created(null).body(result);
    }

    @PutMapping("/{hardwareControllerId}")
    public ResponseEntity<HardwareController> update(@PathVariable("hardwareControllerId") long hardwareControllerId,
                                                     @RequestBody HardwareController hardwareController){
        HardwareController result = this.hardwareControllerAdditionService.update(hardwareControllerId, hardwareController);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/")
    public List<HardwareController> getAllHardwareControllers(){
        return this.hardwareControllerService.getAllHardwareControllers();
    }

    //TODO: this is for the realtime epic
//    @GetMapping(value = "/realtime")
//    public SseEmitter getRealtimeHardwareControllers(){
//        return this.hardwareControllerSubscriptionService.addSubscriber();
//    }

    @GetMapping("/{hardwareControllerId}")
    public ResponseEntity<HardwareController> getHardwareController(@PathVariable long hardwareControllerId){
        HardwareController result = this.hardwareControllerService.getHardwareController(hardwareControllerId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{hardwareControllerId}")
    public ResponseEntity deleteHardwareController(@PathVariable("hardwareControllerId") long hardwareControllerId){
        this.hardwareControllerAdditionService.delete(hardwareControllerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/")
    public ResponseEntity deleteAllHardwareControllers(){
        this.hardwareControllerAdditionService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all hardware for a hardware controller
     * @param hardwareControllerId - the id of the hardware controller
     * @return - all hardware for the hardware controller
     */
    @GetMapping(value = "/{hardwareControllerId}/hardware")
    public ResponseEntity<List<Hardware>> getHardware(@PathVariable("hardwareControllerId") long hardwareControllerId){
        List<Hardware> result = this.hardwareControllerService.getHardware(hardwareControllerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Create a new hardware for a hardware controller
     * @param hardwareControllerId - the id of the hardware controller
     * @param hardware - the hardware to add to the hardware controller
     * @return - the created hardware
     */
    @PostMapping(value = "/{hardwareControllerId}/hardware")
    public ResponseEntity<Hardware> createHardware(@PathVariable("hardwareControllerId") long hardwareControllerId, @RequestBody Hardware hardware){
        Hardware result = this.hardwareControllerAdditionService.addHardware(hardwareControllerId, hardware);
        return ResponseEntity.created(null).body(result);
    }

    @PostMapping(value = "/{hardwareControllerId}/sensor")
    public ResponseEntity<Sensor> createSensor(@PathVariable("hardwareControllerId") long hardwareControllerId, @RequestBody Sensor sensor){
        Sensor result = this.hardwareControllerAdditionService.addSensor(hardwareControllerId, sensor);
        return ResponseEntity.created(null).body(result);
    }

    @GetMapping(value = "/{hardwareControllerId}/sensor")
    public ResponseEntity<List<Sensor>> getSensor(@PathVariable("hardwareControllerId") long hardwareControllerId){
        List<Sensor> result = this.hardwareControllerService.getSensors(hardwareControllerId);
        return ResponseEntity.ok(result);
    }
}
