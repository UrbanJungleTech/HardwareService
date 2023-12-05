package urbanjungletech.hardwareservice.service.router;

import urbanjungletech.hardwareservice.model.SensorReading;

public interface SpecificSensorReadingRouterService<T> {

    void route(T routerData, SensorReading sensorReading);
}
