package urbanjungletech.hardwareservice.event.sensorreading;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import urbanjungletech.hardwareservice.service.query.SensorReadingQueryService;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;
import urbanjungletech.hardwareservice.service.router.SensorReadingRouterService;
import java.util.List;
@Service
public class SensorReadingRouterEventHandler {
    private final SensorReadingRouterQueryService sensorReadingRouterQueryService;
    private final SensorReadingRouterService sensorReadingRouterService;
    private final SensorReadingQueryService sensorReadingQueryService;
    private final SensorQueryService sensorQueryService;
    public SensorReadingRouterEventHandler(SensorReadingRouterQueryService sensorReadingRouterQueryService,
                                           SensorReadingRouterService sensorReadingRouterService,
                                           SensorReadingQueryService sensorReadingQueryService,
                                           SensorQueryService sensorQueryService){
        this.sensorReadingRouterQueryService = sensorReadingRouterQueryService;
        this.sensorReadingRouterService = sensorReadingRouterService;
        this.sensorReadingQueryService = sensorReadingQueryService;
        this.sensorQueryService = sensorQueryService;
    }

    @TransactionalEventListener
    @Async
    public void routeSensorReading(SensorReadingCreateEvent event){
        SensorReading sensorReading = this.sensorReadingQueryService.findById(event.getId());
        Sensor sensor = this.sensorQueryService.getSensor(sensorReading.getSensorId());
        List<SensorReadingRouter> sensorReadingRouters = sensor.getSensorReadingRouters();
        this.sensorReadingRouterService.route(sensorReadingRouters, sensorReading);
    }
}
