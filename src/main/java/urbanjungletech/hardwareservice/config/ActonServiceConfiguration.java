package urbanjungletech.hardwareservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.service.action.SpecificActionExecutionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ActonServiceConfiguration {
    /**
     * Finds all the SpecificActionExecutionService beans and uses the genertic type in their implementation
     * of ActionExecutionService to create a map of the beans where the key is the generic type and the value is the
     * service itself.
     * @return
     */
    @Bean
    public Map<Class, SpecificActionExecutionService> actionExecutionServices(List<SpecificActionExecutionService> actionExecutionServices) {
        Map<Class, SpecificActionExecutionService> actionExecutionServiceMap = new HashMap<>();

        for (SpecificActionExecutionService actionExecutionService : actionExecutionServices) {
            for(Class interfaceType : actionExecutionService.getClass().getInterfaces()) {
                if (interfaceType.equals(SpecificActionExecutionService.class)) {
                    Class actionType = actionExecutionService.getClass().getGenericInterfaces()[0].getClass();
                    actionExecutionServiceMap.put(actionType, actionExecutionService);
                    break;
                }
            }
        }
        return actionExecutionServiceMap;
    }
}
