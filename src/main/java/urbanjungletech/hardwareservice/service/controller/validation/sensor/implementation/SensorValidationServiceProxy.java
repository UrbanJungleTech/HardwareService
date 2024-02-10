package urbanjungletech.hardwareservice.service.controller.validation.sensor.implementation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SensorValidationService;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SpecificSensorValidationService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
@Primary
public class SensorValidationServiceProxy implements SensorValidationService {

    private final Map<Class<? extends HardwareController>, SpecificSensorValidationService> sensorValidationServiceMap;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    public SensorValidationServiceProxy(Map<Class<? extends HardwareController>, SpecificSensorValidationService> sensorValidationServiceMap,
                                        HardwareControllerQueryService hardwareControllerQueryService) {
        this.sensorValidationServiceMap = sensorValidationServiceMap;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }

    @Override
    public SensorValidationError validateSensorType(Sensor sensor) {
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getClass();
        if(this.sensorValidationServiceMap.get(controllerType) == null) {
            return new SensorValidationError();
        }
        else {
            return this.sensorValidationServiceMap.get(controllerType).validateSensorType(sensor);
        }
    }
}
