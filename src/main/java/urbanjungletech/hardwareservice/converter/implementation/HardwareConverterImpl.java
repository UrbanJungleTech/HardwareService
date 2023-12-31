package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.converter.TimerConverter;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareState;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareConverterImpl implements HardwareConverter {

    private final HardwareStateConverter hardwareStateConverter;
    private final TimerConverter timerConverter;

    public HardwareConverterImpl(HardwareStateConverter hardwareStateConverter,
                                 TimerConverter timerConverter){
        this.hardwareStateConverter = hardwareStateConverter;
        this.timerConverter = timerConverter;
    }

    @Override
    public Hardware toModel(HardwareEntity hardwareEntity) {
        Hardware result = new Hardware();
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
        result.setMetadata(hardwareEntity.getMetadata());
        result.setConfiguration(hardwareEntity.getConfiguration());
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
        hardwareEntity.setMetadata(hardware.getMetadata());
        hardwareEntity.setConfiguration(hardware.getConfiguration());
        hardwareEntity.setPort(hardware.getPort());
        hardwareEntity.setHardwareCategory(hardware.getType());
        hardwareEntity.setId(hardware.getId());
        hardwareEntity.setName(hardware.getName());
        hardwareEntity.setOffState(hardware.getOffState());
        hardwareEntity.setPossibleStates(hardware.getPossibleStates());
    }
}
