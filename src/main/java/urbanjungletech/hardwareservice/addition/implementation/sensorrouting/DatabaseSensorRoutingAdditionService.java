package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

import java.util.List;

@Service
public class DatabaseSensorRoutingAdditionService implements AdditionService<DatabaseSensorReadingRouter> {

    private CredentialsAdditionService credentialsAdditionService;

    public DatabaseSensorRoutingAdditionService(CredentialsAdditionService credentialsAdditionService){
        this.credentialsAdditionService = credentialsAdditionService;
    }
    @Override
    public DatabaseSensorReadingRouter create(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        Credentials credentials = this.credentialsAdditionService.create(databaseSensorReadingRouter.getCredentials());
        databaseSensorReadingRouter.setCredentials(credentials);
        return databaseSensorReadingRouter;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public DatabaseSensorReadingRouter update(long id, DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        return null;
    }

    @Override
    public List<DatabaseSensorReadingRouter> updateList(List<DatabaseSensorReadingRouter> models) {
        return null;
    }
}
