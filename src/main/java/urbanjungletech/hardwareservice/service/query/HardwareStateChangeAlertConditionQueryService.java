package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;

import java.util.List;

public interface HardwareStateChangeAlertConditionQueryService {
    List<HardwareStateChangeAlertCondition> findByHardwareStateId(Long hardwareId);
}
