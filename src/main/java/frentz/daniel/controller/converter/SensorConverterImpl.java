package frentz.daniel.controller.converter;

import frentz.daniel.controller.entity.SensorEntity;
import frentz.daniel.controllerclient.model.Sensor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorConverterImpl implements SensorConverter{
    @Override
    public Sensor toModel(SensorEntity sensorEntity) {
        Sensor result = new Sensor();
        result.setId(sensorEntity.getId());
        result.setSensorTypes(sensorEntity.getSensorTypes());
        result.setHardwareControllerId(sensorEntity.getHardwareController().getId());
        result.setSerialNumber(sensorEntity.getSerialNumber());
        return result;
    }

    @Override
    public List<Sensor> toModels(List<SensorEntity> sensorEntities) {
        if(sensorEntities != null) {
            return sensorEntities.stream().map((sensorEntity -> {
                return this.toModel(sensorEntity);
            })).collect(Collectors.toList());
        }
        return null;
    }
}
