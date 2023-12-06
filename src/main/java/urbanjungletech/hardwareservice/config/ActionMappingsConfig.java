package urbanjungletech.hardwareservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificSensorRouterAdditionService;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.credentials.retrieval.SpecificCredentialRetrievalService;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ActionMappingsConfig {



    @Bean
    public Map<Class <? extends AlertAction>, SpecificActionExecutionService>
    actionMappings(List<SpecificActionExecutionService> actionExecutionServices, MapGeneratorService mapGeneratorService){
        Map<Class <? extends AlertAction>, SpecificActionExecutionService> result = mapGeneratorService.generateMap(actionExecutionServices, SpecificActionExecutionService.class);
        return result;
    }

    @Bean
    public Map<Class <? extends SensorReadingRouter>, SpecificSensorReadingRouterService>
    sensorReadingRouterServices(List<SpecificSensorReadingRouterService> routerServices,
                                MapGeneratorService mapGeneratorService){
        Map<Class <? extends SensorReadingRouter>, SpecificSensorReadingRouterService> result = mapGeneratorService.generateMap(routerServices, SpecificSensorReadingRouterService.class);
        return result;
    }

    @Bean
    public Map<Class <? extends SensorReadingRouter>, SpecificSensorRouterAdditionService>
    specificSensorRouterAdditionServices(List<SpecificSensorRouterAdditionService> additionServices,
                                MapGeneratorService mapGeneratorService){
        mapGeneratorService.generateMap(additionServices, SensorReadingRouter.class);
        Map<Class <? extends SensorReadingRouter>, SpecificSensorRouterAdditionService> result = mapGeneratorService.generateMap(additionServices, SpecificSensorRouterAdditionService.class);
        return result;
    }

    @Bean
    public Map<Class <? extends Credentials>, SpecificCredentialRetrievalService>
    credentialRetrievalServiceMap(List<SpecificCredentialRetrievalService> retrievalServices,
                                  MapGeneratorService mapGeneratorService){
        Map<Class <? extends Credentials>, SpecificCredentialRetrievalService> result = mapGeneratorService.generateMap(retrievalServices, SpecificCredentialRetrievalService.class);
        return result;
    }



    /**
     * @param actionConverters For each SpecificActionConverter bean, find the type of the Model and Entity parameters,
     *                         and add these 2 entries to a Map of type <Class, SpecificActionConverter>
     *                         where the first entry has the key Model and the 2nd has the key Entity, and both have
     *                         the value of the SpecificActionConverter bean.
     * @return
     */
    @Bean
    public Map<Class, SpecificAlertActionConverter>
    alertActionConverterMappings(List<SpecificAlertActionConverter> actionConverters,
                                 MapGeneratorService mapGeneratorService){
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
     * @param specificAlertConditionConverters For each SpecificAlertConditionConverter bean,
     * find the type of the Model and Entity parameters, and add these 2 entries to a Map of
     * type <Class, SpecificAlertConditionConverter> where the first entry has the key Model
     * and the 2nd has the key Entity, and both have the value of the SpecificAlertConditionConverter
     * bean.
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

    @Bean
    Map<Class, SpecificSensorReadingRouterConverter> sensorReadingRouterConverterMappings(List<SpecificSensorReadingRouterConverter> specificSensorReadingRouterConverters){
        Map<Class, SpecificSensorReadingRouterConverter> result = new HashMap<>();
        for(SpecificSensorReadingRouterConverter specificSensorReadingRouterConverter : specificSensorReadingRouterConverters){
            for(Type type :  specificSensorReadingRouterConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificSensorReadingRouterConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], specificSensorReadingRouterConverter);
                    result.put((Class)p.getActualTypeArguments()[1], specificSensorReadingRouterConverter);
                }
            }
        }
        return result;
    }

    @Bean
    Map<Class, SpecificCredentialsConverter> credentialsConverterMappings(List<SpecificCredentialsConverter> specificCredentialsConverters){
        Map<Class, SpecificCredentialsConverter> result = new HashMap<>();
        for(SpecificCredentialsConverter credentialsConverter : specificCredentialsConverters){
            for(Type type :  credentialsConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificCredentialsConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], credentialsConverter);
                    result.put((Class)p.getActualTypeArguments()[1], credentialsConverter);
                }
            }
        }
        return result;
    }
}
