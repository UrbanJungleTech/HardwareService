package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.HardwareControllerEntity;
import frentz.daniel.controllerclient.model.HardwareController;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareControllerConverterImpl implements HardwareControllerConverter{

    private HardwareConverter hardwareConverter;
    private SensorConverter sensorConverter;

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
}
