package urbanjungletech.hardwareservice.config;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.action.ActionExecutionService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapGeneratorServiceImpl implements MapGeneratorService{
    @Override
    public <ModelClass, ServiceClass> Map<ModelClass, ServiceClass> generateMap(List<ServiceClass> serviceClasses, Class serviceClass) {
        Map<ModelClass, ServiceClass> result = new HashMap<>();
        for(ServiceClass service : serviceClasses){
            for(Type type :  service.getClass().getGenericInterfaces()){
                if(type instanceof ParameterizedType p && p.getRawType() == serviceClass){
                    result.put((ModelClass)p.getActualTypeArguments()[0], service);
                    break;
                }
            }
        }
        return result;
    }
}
