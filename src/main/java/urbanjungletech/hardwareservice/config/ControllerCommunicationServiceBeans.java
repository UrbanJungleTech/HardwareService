package urbanjungletech.hardwareservice.config;

import urbanjungletech.hardwareservice.service.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ControllerCommunicationServiceBeans {

    @Bean("ControllerCommunicationServices")
    public Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices(ControllerConfiguration controllerConfiguration,
                                                                                       Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServicesByProtocol){
        Map<String, ControllerCommunicationServiceImplementation> result = new HashMap<>();
        for(String type : controllerConfiguration.getTypes().keySet()) {
            ControllerTypeConfiguration typeConfiguration = controllerConfiguration.getTypes().get(type);
            ControllerCommunicationServiceImplementation controllerCommunicationService = controllerCommunicationServicesByProtocol.get(typeConfiguration.getProtocol());
            result.put(type, controllerCommunicationService);
        }
        return result;
    }
}
