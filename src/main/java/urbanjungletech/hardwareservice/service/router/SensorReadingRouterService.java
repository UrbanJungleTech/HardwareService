package urbanjungletech.hardwareservice.service.router;

import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.List;

public interface SensorReadingRouterService {
    void route(List<SensorReadingRouter> sensorReadingRouters, SensorReading sensorReading);
}
