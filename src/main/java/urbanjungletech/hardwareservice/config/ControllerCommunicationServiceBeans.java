package urbanjungletech.hardwareservice.config;

import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SensorValidationServiceImplementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ControllerCommunicationServiceBeans {

    /**
     * Look through all of the ControllerCommunicationServiceImplementation beans and check them for an annotation of type
     * HardwareControllerCommunicationService. If the annotation is present, add the bean to the map such that the key
     * is the value of the type attribute of the annotation and the value is the bean itself.
     * @param controllerConfiguration
     * @param controllerCommunicationServices
     * @return
     */
    @Bean("ControllerCommunicationServices")
    public Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices(List<ControllerCommunicationServiceImplementation> controllerCommunicationServices){
        Map<String, ControllerCommunicationServiceImplementation> result = new HashMap<>();
        for(ControllerCommunicationServiceImplementation controllerCommunicationService : controllerCommunicationServices){
            HardwareControllerCommunicationService hardwareControllerCommunicationService = controllerCommunicationService.getClass().getAnnotation(HardwareControllerCommunicationService.class);
            if(hardwareControllerCommunicationService != null){
                String type = hardwareControllerCommunicationService.type();
                if(hardwareControllerCommunicationService.custom() == true || result.get(type) == null) {
                    result.put(type, controllerCommunicationService);
                }
            }
        }
        return result;
    }

    @Bean("HardwareControllerConfigurationServices")
    public Map<String, ControllerConfigurationServiceImplementation> hardwareControllerConfigurationServices(List<ControllerConfigurationServiceImplementation> controllerConfigurationServices){
        Map<String, ControllerConfigurationServiceImplementation> result = new HashMap<>();
        for(ControllerConfigurationServiceImplementation controllerConfigurationService : controllerConfigurationServices){
            HardwareControllerCommunicationService hardwareControllerCommunicationService = controllerConfigurationService.getClass().getAnnotation(HardwareControllerCommunicationService.class);
            if(hardwareControllerCommunicationService != null){
                String type = hardwareControllerCommunicationService.type();
                if(hardwareControllerCommunicationService.custom() == true || result.get(type) == null) {
                    result.put(type, controllerConfigurationService);
                }
            }
        }
        return result;
    }

    /**
     * Look through all of the SensorValidationServiceImplementation beans and check them for an annotation of type
     * HardwareControllerCommunicationService. If the annotation is present, add the bean to the map such that the key
     * is the value of the type attribute of the annotation and the value is the bean itself.
     */
    @Bean("SensorValidationServices")
    public Map<String, SensorValidationServiceImplementation> sensorValidationServices(List<SensorValidationServiceImplementation> controllerCommunicationServices){
        Map<String, SensorValidationServiceImplementation> result = new HashMap<>();
        for(SensorValidationServiceImplementation controllerCommunicationService : controllerCommunicationServices){
            HardwareControllerCommunicationService hardwareControllerCommunicationService = controllerCommunicationService.getClass().getAnnotation(HardwareControllerCommunicationService.class);
            if(hardwareControllerCommunicationService != null){
                String type = hardwareControllerCommunicationService.type();
                if(hardwareControllerCommunicationService.custom() == true || result.get(type) == null) {
                    result.put(type, controllerCommunicationService);
                }
            }
        }
        return result;
    }
}
