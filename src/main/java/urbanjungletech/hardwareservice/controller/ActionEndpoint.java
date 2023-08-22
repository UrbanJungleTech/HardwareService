package urbanjungletech.hardwareservice.controller;

import urbanjungletech.hardwareservice.action.model.Action;
import urbanjungletech.hardwareservice.addition.ActionAdditionService;
import urbanjungletech.hardwareservice.service.ActionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionEndpoint {
    private ActionAdditionService actionAdditionService;
    private ActionService actionService;

    public ActionEndpoint(ActionAdditionService actionAdditionService,
                          ActionService actionService){
        this.actionAdditionService = actionAdditionService;
        this.actionService = actionService;
    }
    @GetMapping("/{actionId}")
    public ResponseEntity<Action> getAction(@PathVariable("actionId") long actionId){
        Action result = this.actionService.getAction(actionId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<List<Action>> getAllActions(){
        List<Action> result = this.actionService.getAllActions();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<Void> deleteAction(@PathVariable("actionId") long actionId){
        this.actionAdditionService.delete(actionId);
        return ResponseEntity.noContent().build();
    }
}
