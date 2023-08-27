package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.model.HardwareController;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareControllerConverterImpl implements HardwareControllerConverter {

    private final HardwareConverter hardwareConverter;
    private final SensorConverter sensorConverter;

    public HardwareControllerConverterImpl(HardwareConverter hardwareConverter,
                                           SensorConverter sensorConverter){
        this.hardwareConverter = hardwareConverter;
        this.sensorConverter = sensorConverter;
    }

    @Override
    public HardwareController toModel(HardwareControllerEntity hardwareControllerEntity) {
        HardwareController result = new HardwareController();
        result.setId(hardwareControllerEntity.getId());
        result.setName(hardwareControllerEntity.getName());
        result.setSerialNumber(hardwareControllerEntity.getSerialNumber());
        result.setHardware(this.hardwareConverter.toModels(hardwareControllerEntity.getHardware()));
        result.setSensors(this.sensorConverter.toModels(hardwareControllerEntity.getSensors()));
        result.setType(hardwareControllerEntity.getType());
        result.setConfiguration(hardwareControllerEntity.getConfiguration());
        return result;
    }

    @Override
    public List<HardwareController> toModels(List<HardwareControllerEntity> hardwareControllerEntities) {
        List<HardwareController> result = new ArrayList<>();
        for(HardwareControllerEntity entity : hardwareControllerEntities){
            result.add(this.toModel(entity));
        }
        return result;
    }

    @Override
    public void fillEntity(HardwareControllerEntity hardwareControllerEntity, HardwareController hardwareController) {
        hardwareControllerEntity.setName(hardwareController.getName());
        hardwareControllerEntity.setSerialNumber(hardwareController.getSerialNumber());
        hardwareControllerEntity.setType(hardwareController.getType());
        hardwareControllerEntity.setConfiguration(hardwareController.getConfiguration());
    }
}
