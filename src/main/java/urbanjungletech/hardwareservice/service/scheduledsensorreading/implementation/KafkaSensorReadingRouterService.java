package urbanjungletech.hardwareservice.service.scheduledsensorreading.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.ScheduledSensorReading;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.KafkaSensorReadingRouter;
import urbanjungletech.hardwareservice.service.scheduledsensorreading.SpecificSensorReadingRouterService;

@Service
public class KafkaSensorReadingRouterService implements SpecificSensorReadingRouterService<KafkaSensorReadingRouter> {
    @Override
    public void route(KafkaSensorReadingRouter routerData, SensorReading sensorReading) {

    }
}
