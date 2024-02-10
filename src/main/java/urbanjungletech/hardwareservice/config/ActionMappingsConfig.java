package urbanjungletech.hardwareservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.alert.action.SpecificAlertActionConverter;
import urbanjungletech.hardwareservice.converter.alert.condition.SpecificAlertConditionConverter;
import urbanjungletech.hardwareservice.converter.connectiondetails.SpecificConnectionDetailsConverter;
import urbanjungletech.hardwareservice.converter.credentials.SpecificCredentialsConverter;
import urbanjungletech.hardwareservice.converter.hardware.SpecificHardwareConverter;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.SpecificHardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.sensor.SpecificSensorConverter;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SpecificSensorReadingRouterConverter;
import urbanjungletech.hardwareservice.digitaltwins.service.SpecificDigitalTwinsService;
import urbanjungletech.hardwareservice.event.CreateEvent;
import urbanjungletech.hardwareservice.model.alert.action.AlertAction;
import urbanjungletech.hardwareservice.model.alert.condition.AlertCondition;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.alert.action.SpecificActionExecutionService;
import urbanjungletech.hardwareservice.service.alert.condition.service.SpecificConditionTriggerService;
import urbanjungletech.hardwareservice.service.controller.configuration.ControllerConfigurationService;
import urbanjungletech.hardwareservice.service.controller.configuration.implementation.SpecificMqttCredentialsConfigurationService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.SpecificControllerCommunicationService;
import urbanjungletech.hardwareservice.service.controller.validation.sensor.SpecificSensorValidationService;
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
    @ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
    public Map<Class<?extends CreateEvent>, SpecificDigitalTwinsService>
    digitalTwinsMappings(List<SpecificDigitalTwinsService> specificDigitalTwinsServices, MapGeneratorService mapGeneratorService){
        Map<Class <? extends CreateEvent>, SpecificDigitalTwinsService> result = mapGeneratorService.generateMap(specificDigitalTwinsServices, SpecificDigitalTwinsService.class);
        return result;
    }

    @Bean
    public Map<Class <? extends Credentials>, SpecificMqttCredentialsConfigurationService> credentialsConfigurationServices(List<SpecificMqttCredentialsConfigurationService> credentialsConfigurationServices, MapGeneratorService mapGeneratorService){
        Map<Class <? extends Credentials>, SpecificMqttCredentialsConfigurationService> result = mapGeneratorService.generateMap(credentialsConfigurationServices, SpecificMqttCredentialsConfigurationService.class);
        return result;
    }

    @Bean
    public Map<Class <? extends HardwareController>, SpecificControllerCommunicationService>
    controllerCommunicationServiceMap(List<SpecificControllerCommunicationService> controllerCommunicationServices, MapGeneratorService mapGeneratorService){
        Map<Class <? extends HardwareController>, SpecificControllerCommunicationService> result = mapGeneratorService.generateMap(controllerCommunicationServices, SpecificControllerCommunicationService.class);
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
    public Map<Class <? extends AlertCondition>, SpecificConditionTriggerService>
            conditionTriggerServiceMap(List<SpecificConditionTriggerService> routerServices,
                                MapGeneratorService mapGeneratorService){
        return mapGeneratorService.generateMap(routerServices, SpecificConditionTriggerService.class);
    }

    @Bean
    public Map<Class, SpecificAdditionService>
    specificSensorRouterAdditionServices(List<SpecificAdditionService> additionServices,
                                MapGeneratorService mapGeneratorService){
        mapGeneratorService.generateMap(additionServices, SensorReadingRouter.class);
        return mapGeneratorService.generateMap(additionServices, SpecificAdditionService.class);
    }


    @Bean
    public Map<Class <? extends Credentials>, SpecificCredentialRetrievalService>
    credentialRetrievalServiceMap(List<SpecificCredentialRetrievalService> retrievalServices,
                                  MapGeneratorService mapGeneratorService){
        return mapGeneratorService.generateMap(retrievalServices, SpecificCredentialRetrievalService.class);
    }
    @Bean
    public Map<Class<? extends HardwareController>, ControllerConfigurationService>
    controllerConfigurationServiceMap(List<ControllerConfigurationService> controllerConfigurationServices,
                                      MapGeneratorService mapGeneratorService){
        return mapGeneratorService.generateMap(controllerConfigurationServices, ControllerConfigurationService.class);
    }

    /**
     * Hashmap of the validation services for sensors based on their controller type.
     */
    @Bean
    public Map<Class<? extends HardwareController>, SpecificSensorValidationService>
    sensorValidationServiceMap(List<SpecificSensorValidationService> sensorValidationServices,
                               MapGeneratorService mapGeneratorService){
        Map<Class<? extends HardwareController>, SpecificSensorValidationService> result = mapGeneratorService.generateMap(sensorValidationServices, SpecificSensorValidationService.class);
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

    @Bean
    public Map<Class, SpecificConnectionDetailsConverter>
    connectionDetailsConverterMap(List<SpecificConnectionDetailsConverter> connectionDetailsConverters,
                                 MapGeneratorService mapGeneratorService){
        Map<Class, SpecificConnectionDetailsConverter> result = new HashMap<>();
        for(SpecificConnectionDetailsConverter connectionDetailsConverter : connectionDetailsConverters){
            for(Type type :  connectionDetailsConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificConnectionDetailsConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], connectionDetailsConverter);
                    result.put((Class)p.getActualTypeArguments()[1], connectionDetailsConverter);
                }
            }
        }
        return result;
    }

    @Bean
    public Map<Class, SpecificSensorConverter>
    sensorConverterMappings(List<SpecificSensorConverter> sensorConverters,
                                 MapGeneratorService mapGeneratorService){
        Map<Class, SpecificSensorConverter> result = new HashMap<>();
        for(SpecificSensorConverter currentSensorConverter : sensorConverters){
            for(Type type :  currentSensorConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificSensorConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], currentSensorConverter);
                    result.put((Class)p.getActualTypeArguments()[1], currentSensorConverter);
                }
            }
        }
        return result;
    }

    @Bean
    public Map<Class, SpecificHardwareConverter>
    hardwareConverterMappings(List<SpecificHardwareConverter> hardwareConverters,
                            MapGeneratorService mapGeneratorService){
        Map<Class, SpecificHardwareConverter> result = new HashMap<>();
        for(SpecificHardwareConverter currentHardwareConverter : hardwareConverters){
            for(Type type :  currentHardwareConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificHardwareConverter.class){
                    result.put((Class)p.getActualTypeArguments()[0], currentHardwareConverter);
                    result.put((Class)p.getActualTypeArguments()[1], currentHardwareConverter);
                }
            }
        }
        return result;
    }



    @Bean
    public Map<Class, SpecificHardwareControllerConverter>
    hardwareControllerConverterMappings(List<SpecificHardwareControllerConverter> actionConverters){
        Map<Class, SpecificHardwareControllerConverter> result = new HashMap<>();
        for(SpecificHardwareControllerConverter currentActionConverter : actionConverters){
            for(Type type :  currentActionConverter.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == SpecificHardwareControllerConverter.class){
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
