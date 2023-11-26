package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.AlertActionAdditionService;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.service.query.ActionQueryService;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionEndpoint {
    private AlertActionAdditionService alertActionAdditionService;
    private ActionQueryService actionQueryService;

    public ActionEndpoint(AlertActionAdditionService alertActionAdditionService,
                          ActionQueryService actionQueryService){
        this.alertActionAdditionService = alertActionAdditionService;
        this.actionQueryService = actionQueryService;
    }
    @GetMapping("/{actionId}")
    public ResponseEntity<AlertAction> getAction(@PathVariable("actionId") long actionId){
        AlertAction result = this.actionQueryService.getAction(actionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<AlertAction>> getAllActions(){
        List<AlertAction> result = this.actionQueryService.getAllActions();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<Void> deleteAction(@PathVariable("actionId") long actionId){
        this.alertActionAdditionService.delete(actionId);
        return ResponseEntity.noContent().build();
    }
}
