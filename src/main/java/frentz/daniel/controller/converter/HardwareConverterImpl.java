package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareEntity;
import frentz.daniel.controller.entity.HardwareStateEntity;
import frentz.daniel.controller.entity.TimerEntity;
import frentz.daniel.controllerclient.model.Hardware;
import frentz.daniel.controllerclient.model.HardwareState;
import frentz.daniel.controllerclient.model.Timer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareConverterImpl implements HardwareConverter{

    private HardwareStateConverter hardwareStateConverter;
    private TimerConverter timerConverter;

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
        result.setHardwareCategory(hardwareEntity.getHardwareCategory());
        HardwareState currentState = this.hardwareStateConverter.toModel(hardwareEntity.getCurrentState());
        result.setCurrentState(currentState);
        HardwareState desiredState = this.hardwareStateConverter.toModel(hardwareEntity.getDesiredState());
        result.setDesiredState(desiredState);
        result.setHardwareId(hardwareEntity.getHardwareController().getId());
        for(TimerEntity timerEntity : hardwareEntity.getTimers()){
            Timer timer = this.timerConverter.toModel(timerEntity);
            result.getTimers().add(timer);
        }
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
}
