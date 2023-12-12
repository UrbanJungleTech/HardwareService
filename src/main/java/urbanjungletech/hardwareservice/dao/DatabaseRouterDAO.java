package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

import java.util.List;

public interface DatabaseRouterDAO {
    SensorReadingEntity create(SensorReading sensorReading);
    List<SensorReading> getAll(DatabaseSensorReadingRouter sensorReadingRouter);
}
