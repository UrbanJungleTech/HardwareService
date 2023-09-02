package urbanjungletech.hardwareservice.service.controller.validation.sensor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
@Primary
public class SensorValidationServiceProxy implements SensorValidationService {

    private Map<String, SensorValidationServiceImplementation> sensorValidationServiceMap;
    private HardwareControllerQueryService hardwareControllerQueryService;
    public SensorValidationServiceProxy(@Qualifier("SensorValidationServices") Map<String, SensorValidationServiceImplementation> sensorValidationServiceMap,
                                        HardwareControllerQueryService hardwareControllerQueryService) {
        this.sensorValidationServiceMap = sensorValidationServiceMap;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @Override
    public SensorValidationError validateSensorType(Sensor sensor) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getType();
        if(this.sensorValidationServiceMap.get(controllerType) == null) {
            return new SensorValidationError();
        }
        else {
            return this.sensorValidationServiceMap.get(controllerType).validateSensorType(sensor);
        }
    }
}
