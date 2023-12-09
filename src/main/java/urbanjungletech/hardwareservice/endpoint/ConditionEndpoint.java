package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.service.query.AlertConditionQueryService;

@RestController()
@RequestMapping("/condition")
public class ConditionEndpoint {
    private final AlertConditionAdditionService alertConditionAdditionService;
    private final AlertConditionQueryService alertConditionQueryService;

    public ConditionEndpoint(AlertConditionAdditionService alertConditionAdditionService,
                             AlertConditionQueryService alertConditionQueryService) {
        this.alertConditionAdditionService = alertConditionAdditionService;
        this.alertConditionQueryService = alertConditionQueryService;
    }

    @PostMapping("/")
    public ResponseEntity<AlertCondition> create(@RequestBody AlertCondition alertCondition) {
        AlertCondition response = this.alertConditionAdditionService.create(alertCondition);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{conditionId}")
    public ResponseEntity<AlertCondition> getCondition(@PathVariable("conditionId") long conditionId){
        AlertCondition result = this.alertConditionQueryService.getAlertCondition(conditionId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{conditionId}")
    public ResponseEntity<Void> deleteCondition(@PathVariable("conditionId") long conditionId){
        this.alertConditionAdditionService.delete(conditionId);
        return ResponseEntity.noContent().build();
    }
}
