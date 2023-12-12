package urbanjungletech.hardwareservice.config;

import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

@Service
public class MapGeneratorServiceImpl implements MapGeneratorService{
    @Override
    public <ModelClass, ServiceClass> Map<ModelClass, ServiceClass> generateMap(List<ServiceClass> serviceClasses, Class serviceClass) {
        Map<ModelClass, ServiceClass> result = new HashMap<>();
        for(ServiceClass service : serviceClasses){
            List<Type> possibleTypes = new LinkedList<>();
            possibleTypes.addAll(Arrays.stream(service.getClass().getGenericInterfaces()).toList());
            if(service.getClass().getGenericSuperclass() != null){
                Arrays.stream(service.getClass().getSuperclass().getGenericInterfaces()).toList().forEach(possibleTypes::add);
            }
            for(Type type :  possibleTypes){
                if(type instanceof ParameterizedType p && p.getRawType() == serviceClass){
                    result.put((ModelClass)p.getActualTypeArguments()[0], service);
                    break;
                }
            }
        }
        return result;
    }
}
