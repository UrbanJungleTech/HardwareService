package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class DatabaseSensorReadingRouterService implements SpecificSensorReadingRouterService<DatabaseSensorReadingRouter> {

    private final SensorReadingDAO sensorReadingDAO;

    public DatabaseSensorReadingRouterService(SensorReadingDAO sensorReadingDAO){
        this.sensorReadingDAO = sensorReadingDAO;
    }

    @Override
    public void route(DatabaseSensorReadingRouter sensorReadingRouter, SensorReading sensorReading) {
        this.sensorReadingDAO.createAndSave(sensorReading);
    }
}
