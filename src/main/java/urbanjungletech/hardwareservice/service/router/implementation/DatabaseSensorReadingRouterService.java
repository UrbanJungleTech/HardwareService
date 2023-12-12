package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import urbanjungletech.hardwareservice.dao.DatabaseRouterDAO;
import urbanjungletech.hardwareservice.entity.SensorReadingEntity;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.credentials.generator.implementation.DatasourceClientGenerator;
import urbanjungletech.hardwareservice.service.datasource.DataSourceContext;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

import javax.sql.DataSource;
import java.util.Map;

@Service
public class DatabaseSensorReadingRouterService implements SpecificSensorReadingRouterService<DatabaseSensorReadingRouter> {

    private final DatabaseRouterDAO databaseRouterDAO;
    private final DatasourceClientGenerator datasourceClientGenerator;
    private final DataSourceContext dataSourceContext;
    private final TransactionTemplate transactionTemplate;

    public DatabaseSensorReadingRouterService(DatabaseRouterDAO databaseRouterDAO,
                                              DatasourceClientGenerator datasourceClientGenerator,
                                              TransactionTemplate  transactionTemplate,
                                              DataSourceContext dataSourceContext) {
        this.databaseRouterDAO = databaseRouterDAO;
        this.datasourceClientGenerator = datasourceClientGenerator;
        this.transactionTemplate = transactionTemplate;
        this.dataSourceContext = dataSourceContext;
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    @Transactional
    public void route(DatabaseSensorReadingRouter sensorReadingRouter, SensorReading sensorReading) {
        dataSourceContext.addDataSource(sensorReadingRouter.getCredentials());
        dataSourceContext.setDataSource(sensorReadingRouter.getCredentials());
        this.transactionTemplate.execute((sensorRead) -> {
            return this.databaseRouterDAO.create(sensorReading);
        });
    }
}
