package urbanjungletech.hardwareservice.converter.sensor.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.entity.sensor.CpuSensorEntity;
import urbanjungletech.hardwareservice.model.sensor.CpuSensor;

@Service
public class CpuSensorConverter implements SpecificSensorConverter<CpuSensor, CpuSensorEntity> {
    @Override
    public CpuSensor toModel(CpuSensorEntity sensor) {
        CpuSensor result = new CpuSensor();
        return result;
    }

    @Override
    public CpuSensorEntity createEntity(CpuSensor sensor) {
        CpuSensorEntity result = new CpuSensorEntity();
        return result;
    }

    @Override
    public void fillEntity(CpuSensorEntity sensorEntity, CpuSensor sensor) {

    }
}
