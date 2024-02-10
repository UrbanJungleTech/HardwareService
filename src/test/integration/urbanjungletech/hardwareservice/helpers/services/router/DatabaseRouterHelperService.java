package urbanjungletech.hardwareservice.helpers.services.router;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.connectiondetails.DatabaseConnectionDetails;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

@Service
public class DatabaseRouterHelperService {
    public DatabaseSensorReadingRouter getDatabaseRouter() {
        DatabaseSensorReadingRouter databaseSensorReadingRouter = new DatabaseSensorReadingRouter();
        DatabaseCredentials credentials = new DatabaseCredentials();
        credentials.setUsername("test");
        credentials.setPassword("test");
        DatabaseConnectionDetails connectionDetails = new DatabaseConnectionDetails();
        connectionDetails.setPort(3306);
        connectionDetails.setDatabase("sensorReadingRouterTest");
        //use in memory h2 database
        connectionDetails.setDriver("org.h2.Driver");
        connectionDetails.setDialect("org.hibernate.dialect.H2Dialect");
        connectionDetails.setUrl("jdbc:h2:mem:testRouterdb");

        connectionDetails.setCredentials(credentials);
        databaseSensorReadingRouter.setDatabaseConnectionDetails(connectionDetails);
        databaseSensorReadingRouter.setTableName("SENSORREADINGENTITY");
        databaseSensorReadingRouter.setValueColumn("testValueColumn");
        databaseSensorReadingRouter.setTimestampColumn("testTimestampColumn");
        return databaseSensorReadingRouter;
    }
}
