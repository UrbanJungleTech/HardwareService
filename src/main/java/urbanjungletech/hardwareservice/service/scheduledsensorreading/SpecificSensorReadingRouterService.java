package urbanjungletech.hardwareservice.service.scheduledsensorreading;

import urbanjungletech.hardwareservice.model.SensorReading;

public interface SpecificSensorReadingRouterService<T> {

    void route(T routerData, SensorReading sensorReading);
}
