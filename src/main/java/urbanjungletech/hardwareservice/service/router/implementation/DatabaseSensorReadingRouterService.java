package urbanjungletech.hardwareservice.service.router.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import urbanjungletech.hardwareservice.dao.DatabaseRouterDAO;
import urbanjungletech.hardwareservice.model.SensorReading;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.DatabaseSensorReadingRouter;
import urbanjungletech.hardwareservice.service.datasource.DataSourceContext;
import urbanjungletech.hardwareservice.service.router.SpecificSensorReadingRouterService;

@Service
public class DatabaseSensorReadingRouterService implements SpecificSensorReadingRouterService<DatabaseSensorReadingRouter> {

    private final DatabaseRouterDAO databaseRouterDAO;
    private final DataSourceContext dataSourceContext;
    private final TransactionTemplate transactionTemplate;

    public DatabaseSensorReadingRouterService(DatabaseRouterDAO databaseRouterDAO,
                                              TransactionTemplate  transactionTemplate,
                                              DataSourceContext dataSourceContext) {
        this.databaseRouterDAO = databaseRouterDAO;
        this.transactionTemplate = transactionTemplate;
        this.dataSourceContext = dataSourceContext;
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRES_NEW);
    }

    @Override
    @Transactional
    public void route(DatabaseSensorReadingRouter sensorReadingRouter, SensorReading sensorReading) {
        dataSourceContext.addDataSource(sensorReadingRouter.getCredentials());
        dataSourceContext.setDataSource(sensorReadingRouter.getCredentials());
        this.transactionTemplate.execute((sensorRead) -> this.databaseRouterDAO.create(sensorReading));
    }
}
