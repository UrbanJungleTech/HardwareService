package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.HardwareStateAdditionService;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.service.query.HardwareStateQueryService;

@RestController()
@RequestMapping(value = "/hardwarestate")
public class HardwareStateEndpoint {

    private HardwareStateQueryService hardwareStateQueryService;
    private HardwareStateAdditionService hardwareAdditionService;

    public HardwareStateEndpoint(HardwareStateQueryService hardwareStateQueryService,
                                 HardwareStateAdditionService hardwareAdditionService){
        this.hardwareStateQueryService = hardwareStateQueryService;
        this.hardwareAdditionService = hardwareAdditionService;

    }

    @GetMapping("/{hardwareStateId}")
    public ResponseEntity<HardwareState> getHardwareStateById(@PathVariable long hardwareStateId){
        HardwareState result = this.hardwareStateQueryService.getHardwareStateById(hardwareStateId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{hardwareStateId}")
    public ResponseEntity<HardwareState> updateHardwareState(@PathVariable long hardwareStateId, @RequestBody HardwareState hardwareState){
        HardwareState result = this.hardwareAdditionService.update(hardwareStateId, hardwareState);
        return ResponseEntity.ok(result);
    }
}
