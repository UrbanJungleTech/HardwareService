package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareStateEntity;
import frentz.daniel.controllerclient.model.HardwareState;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateConverterImpl implements HardwareStateConverter{
    @Override
    public HardwareState toModel(HardwareStateEntity hardwareState) {
        HardwareState result = new HardwareState();
        result.setLevel(hardwareState.getLevel());
        result.setState(hardwareState.getState());
        return result;
    }

    @Override
    public HardwareStateEntity toEntity(HardwareState hardwareState) {
        HardwareStateEntity result = new HardwareStateEntity();
        result.setState(hardwareState.getState());
        result.setLevel(hardwareState.getLevel());
        return result;
    }
}
