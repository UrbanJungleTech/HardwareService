package urbanjungletech.hardwareservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.model.alert.Alert;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.service.query.AlertQueryService;

import java.util.List;
import java.util.Map;

@Configuration
public class ConditionAlertMapConfig {

    @Bean
    public Map<AlertCondition, Alert> conditionAlertMap(List<AlertCondition> conditions, AlertQueryService alertQueryService) {
        Map<AlertCondition, Alert> result = new java.util.HashMap<>();
        for (AlertCondition condition : conditions) {
            Alert alert = alertQueryService.getSensorReadingAlert(condition.getAlertId());
            result.put(condition, alert);
        }
        return result;
    }
}
