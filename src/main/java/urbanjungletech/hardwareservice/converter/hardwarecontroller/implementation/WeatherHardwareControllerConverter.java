package urbanjungletech.hardwareservice.converter.hardwarecontroller.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.connectiondetails.implementation.WeatherConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.entity.connectiondetails.WeatherConnectionDetailsEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.WeatherHardwareControllerEntity;
import urbanjungletech.hardwareservice.model.connectiondetails.WeatherConnectionDetails;
import urbanjungletech.hardwareservice.model.hardwarecontroller.WeatherHardwareController;
import urbanjungletech.hardwareservice.repository.ConnectionDetailsRepository;

import java.util.Optional;

@Service
public class WeatherHardwareControllerConverter implements SpecificHardwareControllerConverter<WeatherHardwareController, WeatherHardwareControllerEntity> {

    private final WeatherConnectionDetailsConverter connectionDetailsConverter;
    private final ConnectionDetailsRepository connectionDetailsRepository;


    public WeatherHardwareControllerConverter(WeatherConnectionDetailsConverter connectionDetailsConverter,
                                              ConnectionDetailsRepository connectionDetailsRepository){
        this.connectionDetailsConverter = connectionDetailsConverter;
        this.connectionDetailsRepository = connectionDetailsRepository;
    }

    @Override
    public void fillEntity(WeatherHardwareControllerEntity hardwareControllerEntity, WeatherHardwareController hardwareController) {
        WeatherConnectionDetails connectionDetails = hardwareController.getConnectionDetails();
        Optional.ofNullable(connectionDetails.getId()).ifPresent((id) -> {
                WeatherConnectionDetailsEntity connectionDetailsEntity = (WeatherConnectionDetailsEntity) this.connectionDetailsRepository.findById(connectionDetails.getId())
                .orElseThrow(() -> new RuntimeException("Connection details not found"));
            hardwareControllerEntity.setConnectionDetails(connectionDetailsEntity);
        });
    }

    @Override
    public WeatherHardwareController toModel(WeatherHardwareControllerEntity hardwareControllerEntity) {
        WeatherHardwareController result = new WeatherHardwareController();
        result.setConnectionDetails(this.connectionDetailsConverter.toModel(hardwareControllerEntity.getConnectionDetails()));
        return result;
    }

    @Override
    public WeatherHardwareControllerEntity createEntity(WeatherHardwareController hardwareController) {
        WeatherHardwareControllerEntity result = new WeatherHardwareControllerEntity();
        this.fillEntity(result, hardwareController);
        return result;
    }
}
