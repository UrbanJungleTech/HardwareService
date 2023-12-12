package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.dao.DatabaseRouterDAO;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.credentials.generator.implementation.DatasourceClientGenerator;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseSensorRoutingAdditionService implements SpecificSensorRouterAdditionService<DatabaseSensorReadingRouter> {

    private final CredentialsAdditionService credentialsAdditionService;
    private final DatabaseRouterDAO databaseRouterDAO;
    private final DatasourceClientGenerator datasourceClientGenerator;
    private final Map<Object, Object> targetDataSources;

    public DatabaseSensorRoutingAdditionService(CredentialsAdditionService credentialsAdditionService,
                                                DatabaseRouterDAO databaseRouterDAO,
                                                DatasourceClientGenerator datasourceClientGenerator,
                                                Map<Object, Object> targetDataSources){
        this.credentialsAdditionService = credentialsAdditionService;
        this.databaseRouterDAO = databaseRouterDAO;
        this.datasourceClientGenerator = datasourceClientGenerator;
        this.targetDataSources = targetDataSources;
    }
    @Override
    public DatabaseSensorReadingRouter create(DatabaseSensorReadingRouter databaseSensorReadingRouter) {
        DatabaseCredentials credentials = (DatabaseCredentials)this.credentialsAdditionService.create(databaseSensorReadingRouter.getCredentials());
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
}
