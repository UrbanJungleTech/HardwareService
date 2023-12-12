package urbanjungletech.hardwareservice.services.router;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.credentials.DatabaseCredentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;

@Service
public class DatabaseRouterHelperService {
    public DatabaseSensorReadingRouter getDatabaseRouter() {
        DatabaseSensorReadingRouter databaseSensorReadingRouter = new DatabaseSensorReadingRouter();
        DatabaseCredentials credentials = new DatabaseCredentials();
        credentials.setUsername("test");
        credentials.setPassword("test");
        credentials.setPort("3306");
        credentials.setDatabase("sensorReadingRouterTest");
        //use in memory h2 database
        credentials.setDriver("org.h2.Driver");
        credentials.setDialect("org.hibernate.dialect.H2Dialect");
        credentials.setHost("jdbc:h2:mem:testRouterdb");

        databaseSensorReadingRouter.setCredentials(credentials);
        databaseSensorReadingRouter.setTableName("SENSORREADINGENTITY");
        databaseSensorReadingRouter.setValueColumn("testValueColumn");
        databaseSensorReadingRouter.setTimestampColumn("testTimestampColumn");
        return databaseSensorReadingRouter;
    }
}
