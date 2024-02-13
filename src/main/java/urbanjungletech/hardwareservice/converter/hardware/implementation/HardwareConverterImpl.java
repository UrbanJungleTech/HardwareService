package urbanjungletech.hardwareservice.converter.hardware.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.converter.hardware.HardwareConverter;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HardwareConverterImpl implements HardwareConverter {

    private final HardwareStateConverter hardwareStateConverter;
    private final TimerConverter timerConverter;
    private final Map<Class, SpecificHardwareConverter> specificHardwareConverters;

    public HardwareConverterImpl(HardwareStateConverter hardwareStateConverter,
                                 TimerConverter timerConverter,
                                 Map<Class, SpecificHardwareConverter> specificHardwareConverters){
        this.hardwareStateConverter = hardwareStateConverter;
        this.timerConverter = timerConverter;
        this.specificHardwareConverters = specificHardwareConverters;
    }

    @Override
    public Hardware toModel(HardwareEntity hardwareEntity) {
        Hardware result = this.specificHardwareConverters.get(hardwareEntity.getClass()).toModel(hardwareEntity);
        result.setId(hardwareEntity.getId());
        result.setName(hardwareEntity.getName());
        result.setPort(hardwareEntity.getPort());
        result.setType(hardwareEntity.getHardwareCategory());
        HardwareState currentState = this.hardwareStateConverter.toModel(hardwareEntity.getCurrentState());
        result.setCurrentState(currentState);
        HardwareState desiredState = this.hardwareStateConverter.toModel(hardwareEntity.getDesiredState());
        result.setDesiredState(desiredState);
        result.setHardwareControllerId(hardwareEntity.getHardwareController().getId());
        result.setTimers(this.timerConverter.toModels(hardwareEntity.getTimers()));
        result.setPossibleStates(hardwareEntity.getPossibleStates());
        result.setOffState(hardwareEntity.getOffState());
        return result;
    }

    @Override
    public List<Hardware> toModels(List<HardwareEntity> entities){
        List<Hardware> result = new ArrayList<>();
        for(HardwareEntity entity : entities){
            result.add(this.toModel(entity));
        }
        return result;
    }

    @Override
    public void fillEntity(HardwareEntity hardwareEntity, Hardware hardware) {
        hardwareEntity.setHardwareCategory(hardware.getType());
        hardwareEntity.setPort(hardware.getPort());
        hardwareEntity.setHardwareCategory(hardware.getType());
        hardwareEntity.setId(hardware.getId());
        hardwareEntity.setName(hardware.getName());
        hardwareEntity.setOffState(hardware.getOffState());
        hardwareEntity.setPossibleStates(hardware.getPossibleStates());
    }

    @Override
    public HardwareEntity createEntity(Hardware hardware) {
        return this.specificHardwareConverters.get(hardware.getClass()).createEntity(hardware);
    }
}
