package urbanjungletech.hardwareservice.controller;

import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.HardwareStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "/hardwarestate")
public class HardwareStateEndpoint {

    private HardwareStateService hardwareStateService;
    private HardwareStateAdditionService hardwareAdditionService;

    public HardwareStateEndpoint(HardwareStateService hardwareStateService,
                                 HardwareStateAdditionService hardwareAdditionService){
        this.hardwareStateService = hardwareStateService;
        this.hardwareAdditionService = hardwareAdditionService;

    }

    @GetMapping("/{hardwareStateId}")
    public ResponseEntity<HardwareState> getHardwareStateById(@PathVariable long hardwareStateId){
        HardwareState result = this.hardwareStateService.getHardwareStateById(hardwareStateId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{hardwareStateId}")
    public ResponseEntity<HardwareState> updateHardwareState(@PathVariable long hardwareStateId, @RequestBody HardwareState hardwareState){
        HardwareState result = this.hardwareAdditionService.update(hardwareStateId, hardwareState);
        return ResponseEntity.ok(result);
    }
}
