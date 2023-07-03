package frentz.daniel.hardwareservice.controller;

import frentz.daniel.hardwareservice.model.HardwareState;
import frentz.daniel.hardwareservice.service.HardwareStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/hardwarestate")
public class HardwareStateEndpoint {

    private HardwareStateService hardwareStateService;

    public HardwareStateEndpoint(HardwareStateService hardwareStateService){
        this.hardwareStateService = hardwareStateService;
    }

    @GetMapping("/{hardwareStateId}")
    public ResponseEntity<HardwareState> getHardwareStateById(@PathVariable long hardwareStateId){
        HardwareState result = this.hardwareStateService.getHardwareStateById(hardwareStateId);
        return ResponseEntity.ok(result);
    }
}
