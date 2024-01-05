package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HardwareControllerConverterImpl implements HardwareControllerConverter {

    private final HardwareConverter hardwareConverter;
    private final SensorConverter sensorConverter;
    private final Map<Class, SpecificHardwareControllerConverter> specificHardwareControllerConverters;


    public HardwareControllerConverterImpl(HardwareConverter hardwareConverter,
                                           SensorConverter sensorConverter,
                                           Map<Class, SpecificHardwareControllerConverter> specificHardwareControllerConverters){
        this.hardwareConverter = hardwareConverter;
        this.sensorConverter = sensorConverter;
        this.specificHardwareControllerConverters = specificHardwareControllerConverters;
    }

    @Override
    public HardwareController toModel(HardwareControllerEntity hardwareControllerEntity) {
        SpecificHardwareControllerConverter specificHardwareControllerConverter = this.specificHardwareControllerConverters.get(hardwareControllerEntity.getClass());
        HardwareController result = specificHardwareControllerConverter.toModel(hardwareControllerEntity);
        result.setId(hardwareControllerEntity.getId());
        result.setName(hardwareControllerEntity.getName());
        result.setHardware(this.hardwareConverter.toModels(hardwareControllerEntity.getHardware()));
        result.setSensors(this.sensorConverter.toModels(hardwareControllerEntity.getSensors()));
        result.setType(hardwareControllerEntity.getType());
        result.setSerialNumber(hardwareControllerEntity.getSerialNumber());
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
        this.specificHardwareControllerConverters.get(hardwareController.getClass()).fillEntity(hardwareControllerEntity, hardwareController);
        hardwareControllerEntity.setName(hardwareController.getName());
        hardwareControllerEntity.setType(hardwareController.getType());
        hardwareControllerEntity.setSerialNumber(hardwareController.getSerialNumber());
    }

    @Override
    public HardwareControllerEntity createEntity(HardwareController hardwareController) {
        HardwareControllerEntity result = this.specificHardwareControllerConverters.get(hardwareController.getClass()).createEntity(hardwareController);
        this.fillEntity(result, hardwareController);
        return result;
    }
}
