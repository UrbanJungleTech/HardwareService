package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.addition.HardwareAdditionService;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hardware/{hardwareId}/state")
public class HardwareStateEndpoint {

    private HardwareAdditionService hardwareAdditionService;

    /**
     * Update the hardware state
     */
    @PutMapping("/")
    public ResponseEntity<HardwareState> updateHardwareState(@RequestBody HardwareState hardwareState){
        return ResponseEntity.ok(hardwareState);
    }
}
