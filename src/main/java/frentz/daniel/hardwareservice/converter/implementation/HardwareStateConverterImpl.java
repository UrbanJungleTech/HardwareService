package frentz.daniel.hardwareservice.converter.implementation;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.entity.HardwareStateEntity;
import frentz.daniel.hardwareservice.model.HardwareState;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateConverterImpl implements HardwareStateConverter {
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

    @Override
    public void fillEntity(HardwareStateEntity hardwareStateEntity, HardwareState hardwareState) {
        hardwareStateEntity.setState(hardwareState.getState());
        hardwareStateEntity.setLevel(hardwareState.getLevel());
    }
}
