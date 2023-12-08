package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.alert.condition.AlertConditionConverter;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.alert.condition.HardwareStateChangeAlertCondition;
import urbanjungletech.hardwareservice.repository.HardwareStateChangeAlertConditionRepository;
import urbanjungletech.hardwareservice.service.query.HardwareStateChangeAlertConditionQueryService;

import java.util.List;

@Service
public class HardwareStateChangeAlertConditionQueryServiceImpl implements HardwareStateChangeAlertConditionQueryService {
    private final HardwareStateChangeAlertConditionRepository hardwareStateChangeAlertConditionRepository;
    private final ExceptionService exceptionService;
    private final AlertConditionConverter alertConditionConverter;

    public HardwareStateChangeAlertConditionQueryServiceImpl(HardwareStateChangeAlertConditionRepository hardwareStateChangeAlertConditionRepository,
                                                             ExceptionService exceptionService,
                                                             AlertConditionConverter alertConditionConverter) {
        this.hardwareStateChangeAlertConditionRepository = hardwareStateChangeAlertConditionRepository;
        this.exceptionService = exceptionService;
        this.alertConditionConverter = alertConditionConverter;
    }


    @Override
    public List<HardwareStateChangeAlertCondition> findByHardwareStateId(Long hardwareStateId) {
        return this.hardwareStateChangeAlertConditionRepository.findByHardwareId(hardwareStateId)
                .stream()
                .map((condition) -> {
                    return (HardwareStateChangeAlertCondition) this.alertConditionConverter.toModel(condition);
                })
                .toList();
    }
}
