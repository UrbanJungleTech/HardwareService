package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareConverter;
import urbanjungletech.hardwareservice.converter.SensorConverter;
import urbanjungletech.hardwareservice.converter.credentials.CredentialsConverter;
import urbanjungletech.hardwareservice.dao.CredentialsDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.credentials.CredentialsEntity;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.credentials.Credentials;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HardwareControllerConverterImpl implements HardwareControllerConverter {

    private final HardwareConverter hardwareConverter;
    private final SensorConverter sensorConverter;
    private final CredentialsConverter credentialsConverter;
    private final CredentialsDAO credentialsDAO;

    public HardwareControllerConverterImpl(HardwareConverter hardwareConverter,
                                           SensorConverter sensorConverter,
                                           CredentialsConverter credentialsConverter,
                                           CredentialsDAO credentialsDAO){
        this.hardwareConverter = hardwareConverter;
        this.sensorConverter = sensorConverter;
        this.credentialsConverter = credentialsConverter;
        this.credentialsDAO = credentialsDAO;
    }

    @Override
    public HardwareController toModel(HardwareControllerEntity hardwareControllerEntity) {
        HardwareController result = new HardwareController();
        result.setId(hardwareControllerEntity.getId());
        result.setName(hardwareControllerEntity.getName());
        result.setHardware(this.hardwareConverter.toModels(hardwareControllerEntity.getHardware()));
        result.setSensors(this.sensorConverter.toModels(hardwareControllerEntity.getSensors()));
        result.setType(hardwareControllerEntity.getType());
        result.setConfiguration(hardwareControllerEntity.getConfiguration());
        Optional.ofNullable(hardwareControllerEntity.getCredentials()).ifPresent(credentialsEntity -> {
            result.setCredentials(this.credentialsConverter.toModel(credentialsEntity));
        });
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
        hardwareControllerEntity.setType(hardwareController.getType());
        if(hardwareController.getCredentials() != null) {
            CredentialsEntity credentialsEntity = this.credentialsDAO.findById(hardwareController.getCredentials().getId());
            hardwareControllerEntity.setCredentials(credentialsEntity);
        }
        hardwareControllerEntity.setConfiguration(hardwareController.getConfiguration());
    }
}
