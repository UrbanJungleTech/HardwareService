package urbanjungletech.hardwareservice.config;

import urbanjungletech.hardwareservice.converter.SpecificActionConverter;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ActionMappingsConfig {

    @Bean
    public Map<Class, ActionExecutionService> actionMappings(List<ActionExecutionService> actionExecutionServices){
        Map<Class, ActionExecutionService> result = new HashMap<>();
        for(ActionExecutionService currentActionExecutionService : actionExecutionServices){
            for(Type type :  currentActionExecutionService.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == ActionExecutionService.class){
                    result.put((Class)p.getActualTypeArguments()[0], currentActionExecutionService);
                    break;
                }
            }
        }
        return result;
    }


    /**
     * For each
     * @param actionConverters SpecificActionConverter bean, find the type of the Model and Entity parameters,
     *                         and add these 2 entries to a Map of type <Class, SpecificActionConverter>
     *                         where the first entry has the key Model and the 2nd has the key Entity, and both have
     *                         the value of the SpecificActionConverter bean
     * @return
     */
    @Bean
    public Map<Class, SpecificActionConverter> actionConverterMappings(List<SpecificActionConverter> actionConverters){
        Map<Class, SpecificActionConverter> result = new HashMap<>();
        for(SpecificActionConverter currentActionConverter : actionConverters){
            for(Type type :  currentActionConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificActionConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], currentActionConverter);
                    result.put((Class)p.getActualTypeArguments()[1], currentActionConverter);
                }
            }
        }
        return result;
    }
}
