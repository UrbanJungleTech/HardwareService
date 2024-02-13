package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.router.SensorReadingRouterService;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

import java.util.List;
import java.util.Map;

@Primary
@Service
public class SensorReadingRouterServiceProxy implements SensorReadingRouterService {
    private final Map<Class <? extends SensorReadingRouter>, SpecificSensorReadingRouterService> sensorReadingRouterMap;

    public SensorReadingRouterServiceProxy(Map<Class <? extends SensorReadingRouter>, SpecificSensorReadingRouterService> sensorReadingRouterMap) {
        this.sensorReadingRouterMap = sensorReadingRouterMap;
    }
    @Override
    public void route(List<SensorReadingRouter> sensorReadingRouters, SensorReading sensorReading) {
        for (SensorReadingRouter router : sensorReadingRouters) {
            this.sensorReadingRouterMap.get(router.getClass()).route(router, sensorReading);
        }
    }
}
