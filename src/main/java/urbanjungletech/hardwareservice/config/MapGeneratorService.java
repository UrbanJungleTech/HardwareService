package urbanjungletech.hardwareservice.config;

import java.util.List;
import java.util.Map;

public interface MapGeneratorService {
    <ModelClass, ServiceClass> Map<ModelClass, ServiceClass> generateMap(List<ServiceClass> serviceClasses, Class modelClass);
}
