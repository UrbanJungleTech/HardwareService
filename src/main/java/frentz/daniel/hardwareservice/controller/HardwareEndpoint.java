package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.addition.HardwareControllerAdditionService;
import frentz.daniel.hardwareservice.service.HardwareService;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.Timer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hardware")
public class HardwareEndpoint {
    private HardwareAdditionService hardwareAdditionService;
    private HardwareService hardwareService;
    private HardwareControllerAdditionService hardwareControllerAdditionService;

    public HardwareEndpoint(HardwareAdditionService hardwareAdditionService,
                            HardwareService hardwareService,
                            HardwareControllerAdditionService hardwareControllerAdditionService){
        this.hardwareAdditionService = hardwareAdditionService;
        this.hardwareService = hardwareService;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @GetMapping(value = "/{hardwareId}")
    public ResponseEntity<Hardware> getHardware(@PathVariable long hardwareId){
        Hardware hardware = this.hardwareService.getHardware(hardwareId);
        return ResponseEntity.ok(hardware);
    }

    @DeleteMapping("/{hardwareId}")
    public ResponseEntity removeHardware(@PathVariable("hardwareId") long hardwareId){
        this.hardwareAdditionService.delete(hardwareId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{hardwareId}")
    public ResponseEntity<Hardware> updateHardware(@PathVariable("hardwareId") long hardwareId,
                                                   @RequestBody Hardware hardware){
        hardware = this.hardwareAdditionService.update(hardwareId, hardware);
        return ResponseEntity.ok(hardware);
    }

    @PostMapping("/{hardwareId}/timer")
    public ResponseEntity<Timer> addTimer(@RequestBody Timer timer,
                                          @PathVariable("hardwareId") long hardwareId){
        timer = this.hardwareAdditionService.addTimer(hardwareId, timer);
        return ResponseEntity.created(null).body(timer);
    }

}
