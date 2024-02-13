package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.connectiondetails.DatabaseConnectionDetailsAdditionService;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

@Service
public class DatabaseSensorRouterAdditionService implements SpecificAdditionService<DatabaseSensorReadingRouter> {

    private final DatabaseConnectionDetailsAdditionService databaseConnectionDetailsAdditionService;

    public DatabaseSensorRouterAdditionService(DatabaseConnectionDetailsAdditionService databaseConnectionDetailsAdditionService){
        this.databaseConnectionDetailsAdditionService = databaseConnectionDetailsAdditionService;
    }
    @Override
    public void create(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        DatabaseConnectionDetails databaseConnectionDetails = this.databaseConnectionDetailsAdditionService.create(databaseSensorReadingRouter.getDatabaseConnectionDetails());
        databaseSensorReadingRouter.setDatabaseConnectionDetails(databaseConnectionDetails);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(long id, DatabaseSensorReadingRouter databaseSensorReadingRouter) {

    }

}
