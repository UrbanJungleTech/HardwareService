package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.HardwareStateConverter;
import urbanjungletech.hardwareservice.entity.HardwareStateEntity;
import urbanjungletech.hardwareservice.model.HardwareState;
import org.springframework.stereotype.Service;

@Service
public class HardwareStateConverterImpl implements HardwareStateConverter {
    @Override
    public HardwareState toModel(HardwareStateEntity hardwareStateEntity) {
        HardwareState result = new HardwareState();
        result.setId(hardwareStateEntity.getId());
        result.setLevel(hardwareStateEntity.getLevel());
        result.setState(hardwareStateEntity.getState());
        result.setHardwareId(hardwareStateEntity.getHardware().getId());
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
