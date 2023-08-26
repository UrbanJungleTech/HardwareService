package urbanjungletech.hardwareservice.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hardwarecontroller", produces = "application/json")
public class HardwareControllerEndpoint {

    private final Logger logger = LoggerFactory.getLogger(HardwareControllerEndpoint.class);
    private final ObjectMapper objectMapper;
    private final HardwareControllerAdditionService hardwareControllerAdditionService;
    private final HardwareControllerQueryService hardwareControllerQueryService;

    public HardwareControllerEndpoint(ObjectMapper objectMapper,
                                      HardwareControllerAdditionService hardwareControllerAdditionService,
                                      HardwareControllerQueryService hardwareControllerQueryService){
        this.objectMapper = objectMapper;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @PostMapping("/")
    public ResponseEntity<HardwareController> create(@RequestBody HardwareController hardwareController) throws JsonProcessingException {
        HardwareController result = this.hardwareControllerAdditionService.create(hardwareController);
        return ResponseEntity.created(null).body(result);
    }

    /**
     * Update a hardware controller
     * @param hardwareControllerId the id of the existing HardwareController
     * @param hardwareController the updated HardwareController
     * @return the updated HardwareController
     */
    @PutMapping("/{hardwareControllerId}")
    public ResponseEntity<HardwareController> updateHardwareController(@PathVariable("hardwareControllerId") long hardwareControllerId,
                                                     @RequestBody HardwareController hardwareController){
        HardwareController result = this.hardwareControllerAdditionService.update(hardwareControllerId, hardwareController);
        return ResponseEntity.ok(result);
    }

    /**
     * Get all hardware controllers
     * @return All HardwareControllers
     */
    @GetMapping(value = "/")
    public List<HardwareController> getAllHardwareControllers(){
        return this.hardwareControllerQueryService.getAllHardwareControllers();
    }

    /**
     * Get a hardware controller by id
     * @param hardwareControllerId - the id of the hardware controller
     * @return - the hardware controller
     */
    @GetMapping("/{hardwareControllerId}")
    public ResponseEntity<HardwareController> getHardwareControllerById(@PathVariable long hardwareControllerId){
        HardwareController result = this.hardwareControllerQueryService.getHardwareController(hardwareControllerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete a hardware controller by id
     * @param hardwareControllerId - the id of the hardware controller
     * @return - no content on success
     */
    @DeleteMapping("/{hardwareControllerId}")
    public ResponseEntity deleteHardwareControllerById(@PathVariable("hardwareControllerId") long hardwareControllerId){
        this.hardwareControllerAdditionService.delete(hardwareControllerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all hardware controllers
     * @return - no content on success
     */
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
    public ResponseEntity<List<Hardware>> getAllHardwareByHardwareControllerId(@PathVariable("hardwareControllerId") long hardwareControllerId){
        List<Hardware> result = this.hardwareControllerQueryService.getHardware(hardwareControllerId);
        return ResponseEntity.ok(result);
    }

    /**
     * Create a new hardware for a hardware controller
     * @param hardwareControllerId - the id of the hardware controller
     * @param hardware - the hardware to add to the hardware controller
     * @return - the created hardware
     */
    @PostMapping(value = "/{hardwareControllerId}/hardware")
    public ResponseEntity<Hardware> addHardware(@PathVariable("hardwareControllerId") long hardwareControllerId, @RequestBody Hardware hardware){
        Hardware result = this.hardwareControllerAdditionService.addHardware(hardwareControllerId, hardware);
        return ResponseEntity.created(null).body(result);
    }

    @PostMapping(value = "/{hardwareControllerId}/sensor")
    public ResponseEntity<Sensor> addSensor(@PathVariable("hardwareControllerId") long hardwareControllerId, @RequestBody Sensor sensor){
        Sensor result = this.hardwareControllerAdditionService.addSensor(hardwareControllerId, sensor);
        return ResponseEntity.created(null).body(result);
    }

    @GetMapping(value = "/{hardwareControllerId}/sensor")
    public ResponseEntity<List<Sensor>> getSensorByHardwareControllerId(@PathVariable("hardwareControllerId") long hardwareControllerId){
        List<Sensor> result = this.hardwareControllerQueryService.getSensors(hardwareControllerId);
        return ResponseEntity.ok(result);
    }

}
