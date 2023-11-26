package urbanjungletech.hardwareservice.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import urbanjungletech.hardwareservice.addition.AlertConditionAdditionService;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;

@RestController()
@RequestMapping("/condition")
public class AlertConditionEndpoint {
    private final AlertConditionAdditionService alertConditionAdditionService;

    public AlertConditionEndpoint(AlertConditionAdditionService alertConditionAdditionService) {
        this.alertConditionAdditionService = alertConditionAdditionService;
    }
    @PostMapping("/create")
    public ResponseEntity<AlertCondition> create(@RequestBody AlertCondition alertCondition) {
        AlertCondition response = this.alertConditionAdditionService.create(alertCondition);
        return ResponseEntity.ok(response);
    }
}
