package urbanjungletech.hardwareservice.endpoint;

import urbanjungletech.hardwareservice.addition.HardwareControllerGroupAdditionService;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hardwarecontrollergroup")
public class HardwareControllerGroupEndpoint {

    private HardwareControllerGroupAdditionService hardwareControllerGroupAdditionService;

    public HardwareControllerGroupEndpoint(HardwareControllerGroupAdditionService hardwareControllerGroupAdditionService){
        this.hardwareControllerGroupAdditionService = hardwareControllerGroupAdditionService;
    }

    @PostMapping("/")
    public ResponseEntity<HardwareControllerGroup> createHardwareControllerGroup(@RequestBody HardwareControllerGroup hardwareControllerGroup){
        HardwareControllerGroup result = this.hardwareControllerGroupAdditionService.create(hardwareControllerGroup);
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHardwareControllerGroup(@PathVariable("id") long id){
        this.hardwareControllerGroupAdditionService.delete(id);
        return ResponseEntity.ok().build();
    }
}
