package urbanjungletech.hardwareservice.endpoint;

import urbanjungletech.hardwareservice.addition.HardwareControllerGroupAdditionService;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.service.query.HardwareControllerGroupQueryService;

import java.util.List;

@RestController
@RequestMapping("/hardwarecontrollergroup")
public class HardwareControllerGroupEndpoint {

    private HardwareControllerGroupAdditionService hardwareControllerGroupAdditionService;
    private HardwareControllerGroupQueryService hardwareControllerGroupQueryService;

    public HardwareControllerGroupEndpoint(HardwareControllerGroupAdditionService hardwareControllerGroupAdditionService,
                                           HardwareControllerGroupQueryService hardwareControllerGroupQueryService){
        this.hardwareControllerGroupAdditionService = hardwareControllerGroupAdditionService;
        this.hardwareControllerGroupQueryService = hardwareControllerGroupQueryService;
    }

    @PostMapping("/")
    public ResponseEntity<HardwareControllerGroup> createHardwareControllerGroup(@RequestBody HardwareControllerGroup hardwareControllerGroup){
        HardwareControllerGroup result = this.hardwareControllerGroupAdditionService.create(hardwareControllerGroup);
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardwareControllerGroup(@PathVariable("id") long id){
        this.hardwareControllerGroupAdditionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HardwareControllerGroup> getHardwareControllerGroup(@PathVariable("id") long id){
        HardwareControllerGroup result = this.hardwareControllerGroupQueryService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<HardwareControllerGroup>> getHardwareControllerGroups(){
        List<HardwareControllerGroup> result = this.hardwareControllerGroupQueryService.findAll();
        return ResponseEntity.ok(result);
    }
}
