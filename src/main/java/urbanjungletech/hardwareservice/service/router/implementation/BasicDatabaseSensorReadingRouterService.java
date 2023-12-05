package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.dao.SensorReadingDAO;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.BasicDatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class BasicDatabaseSensorReadingRouterService implements SpecificSensorReadingRouterService<BasicDatabaseSensorReadingRouter> {

    private final SensorReadingDAO sensorReadingDAO;

    public BasicDatabaseSensorReadingRouterService(SensorReadingDAO sensorReadingDAO){
        this.sensorReadingDAO = sensorReadingDAO;
    }
    @Override
    public void route(BasicDatabaseSensorReadingRouter routerData, SensorReading sensorReading) {
        this.sensorReadingDAO.createAndSave(sensorReading);
    }
}
