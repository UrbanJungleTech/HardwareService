package urbanjungletech.hardwareservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;

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
    public Map<Class, SpecificAlertActionConverter> alertActionConverterMappings(List<SpecificAlertActionConverter> actionConverters){
        Map<Class, SpecificAlertActionConverter> result = new HashMap<>();
        for(SpecificAlertActionConverter currentActionConverter : actionConverters){
            for(Type type :  currentActionConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificAlertActionConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], currentActionConverter);
                    result.put((Class)p.getActualTypeArguments()[1], currentActionConverter);
                }
            }
        }
        return result;
    }

    /**
     *
     * @param specificAlertConditionConverters
     * @return
     */
    @Bean
    public Map<Class, SpecificAlertConditionConverter> alertConditionConverterMappings(List<SpecificAlertConditionConverter> specificAlertConditionConverters){
        Map<Class, SpecificAlertConditionConverter> result = new HashMap<>();
        for(SpecificAlertConditionConverter specificAlertConditionConverter : specificAlertConditionConverters){
            for(Type type :  specificAlertConditionConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificAlertConditionConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], specificAlertConditionConverter);
                    result.put((Class)p.getActualTypeArguments()[1], specificAlertConditionConverter);
                }
            }
        }
        return result;
    }
}
