package urbanjungletech.hardwareservice.service.scheduledsensorreading.implementation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.scheduledsensorreading.SensorReadingRouterService;
import urbanjungletech.hardwareservice.service.scheduledsensorreading.SpecificSensorReadingRouterService;

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
    public void route(ScheduledSensorReading scheduledSensorReading, SensorReading sensorReading) {
        List<SensorReadingRouter> sensorReadingRouters = scheduledSensorReading.getRouters();
        for (SensorReadingRouter router : sensorReadingRouters) {
            this.sensorReadingRouterMap.get(router.getClass()).route(router, sensorReading);
        }
    }
}
