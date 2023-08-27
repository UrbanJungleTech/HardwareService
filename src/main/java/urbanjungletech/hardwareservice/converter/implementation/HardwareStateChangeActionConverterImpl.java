package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.SpecificActionConverter;
import urbanjungletech.hardwareservice.entity.HardwareStateChangeActionEntity;
import urbanjungletech.hardwareservice.model.HardwareStateChangeAction;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateChangeActionConverterImpl implements SpecificActionConverter<HardwareStateChangeAction, HardwareStateChangeActionEntity> {

    private Logger logger = LoggerFactory.getLogger(HardwareStateChangeActionConverterImpl.class);
    private HardwareStateConverter hardwareStateConverter;

    public HardwareStateChangeActionConverterImpl(HardwareStateConverter hardwareStateConverter) {
        this.hardwareStateConverter = hardwareStateConverter;
    }
    @Override
    public HardwareStateChangeAction toModel(HardwareStateChangeActionEntity hardwareStateChangeActionEntity) {
        HardwareStateChangeAction result = new HardwareStateChangeAction();
        result.setHardwareId(hardwareStateChangeActionEntity.getHardwareId());
//        HardwareState hardwareState = this.hardwareStateConverter.toModel(hardwareStateChangeActionEntity.getHardwareStateEntity());
//        result.setHardwareState(hardwareState);
        return result;
    }

    @Override
    public void fillEntity(HardwareStateChangeActionEntity hardwareStateChangeActionEntity, HardwareStateChangeAction hardwareStateChangeAction) {
        hardwareStateChangeActionEntity.setHardwareId(hardwareStateChangeAction.getHardwareId());
//        hardwareStateChangeActionEntity.setHardwareStateEntity(this.hardwareStateConverter.toEntity(hardwareStateChangeAction.getHardwareState()));
    }

    @Override
    public HardwareStateChangeActionEntity createEntity(HardwareStateChangeAction hardwareStateChangeAction) {
        logger.info("Creating entity of type {}", hardwareStateChangeAction.getClass().getName());
        return new HardwareStateChangeActionEntity();
    }
}
