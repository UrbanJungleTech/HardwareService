package urbanjungletech.hardwareservice.converter.alert.action.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.entity.alert.action.HardwareStateChangeAlertActionEntity;
import urbanjungletech.hardwareservice.model.alert.action.HardwareStateChangeAlertAction;

@Service
public class HardwareStateChangeAlertActionConverterImpl implements SpecificAlertActionConverter<HardwareStateChangeAlertAction, HardwareStateChangeAlertActionEntity> {

    private final Logger logger = LoggerFactory.getLogger(HardwareStateChangeAlertActionConverterImpl.class);
    private final HardwareStateConverter hardwareStateConverter;

    public HardwareStateChangeAlertActionConverterImpl(HardwareStateConverter hardwareStateConverter) {
        this.hardwareStateConverter = hardwareStateConverter;
    }
    @Override
    public HardwareStateChangeAlertAction toModel(HardwareStateChangeAlertActionEntity hardwareStateChangeActionEntity) {
        HardwareStateChangeAlertAction result = new HardwareStateChangeAlertAction();
        result.setHardwareId(hardwareStateChangeActionEntity.getHardwareId());
        result.setOnoff(hardwareStateChangeActionEntity.getOnoff());
        result.setLevel(hardwareStateChangeActionEntity.getLevel());
        return result;
    }

    @Override
    public void fillEntity(HardwareStateChangeAlertActionEntity hardwareStateChangeActionEntity, HardwareStateChangeAlertAction hardwareStateChangeAction) {
        hardwareStateChangeActionEntity.setHardwareId(hardwareStateChangeAction.getHardwareId());
        hardwareStateChangeActionEntity.setOnoff(hardwareStateChangeAction.getOnoff());
        hardwareStateChangeActionEntity.setLevel(hardwareStateChangeAction.getLevel());
    }

    @Override
    public HardwareStateChangeAlertActionEntity createEntity(HardwareStateChangeAlertAction hardwareStateChangeAction) {
        logger.info("Creating entity of type {}", hardwareStateChangeAction.getClass().getName());
        HardwareStateChangeAlertActionEntity result = new HardwareStateChangeAlertActionEntity();
        return result;
    }
}
