package urbanjungletech.hardwareservice.endpoint;

import urbanjungletech.hardwareservice.model.Action;
import urbanjungletech.hardwareservice.addition.ActionAdditionService;
import urbanjungletech.hardwareservice.service.query.ActionQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionEndpoint {
    private ActionAdditionService actionAdditionService;
    private ActionQueryService actionQueryService;

    public ActionEndpoint(ActionAdditionService actionAdditionService,
                          ActionQueryService actionQueryService){
        this.actionAdditionService = actionAdditionService;
        this.actionQueryService = actionQueryService;
    }
    @GetMapping("/{actionId}")
    public ResponseEntity<Action> getAction(@PathVariable("actionId") long actionId){
        Action result = this.actionQueryService.getAction(actionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<Action>> getAllActions(){
        List<Action> result = this.actionQueryService.getAllActions();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<Void> deleteAction(@PathVariable("actionId") long actionId){
        this.actionAdditionService.delete(actionId);
        return ResponseEntity.noContent().build();
    }
}
