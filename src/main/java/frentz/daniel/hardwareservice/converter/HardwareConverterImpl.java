package frentz.daniel.hardwareservice.converter;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareConverterImpl implements HardwareConverter{

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
        hardwareEntity.setCurrentState(this.hardwareStateConverter.toEntity(hardware.getCurrentState()));
        hardwareEntity.setDesiredState(this.hardwareStateConverter.toEntity(hardware.getDesiredState()));
        hardwareEntity.setMetadata(hardware.getMetadata());
        hardwareEntity.setPort(hardware.getPort());
        hardwareEntity.setHardwareCategory(hardware.getType());
        hardwareEntity.setId(hardware.getId());
        hardwareEntity.setName(hardware.getName());
    }
}
