package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

public interface SensorReadingRouterDAO {
    SensorReadingRouterEntity create(SensorReadingRouter sensorReadingRouter);
    SensorReadingRouterEntity update(SensorReadingRouter sensorReadingRouter);
}
