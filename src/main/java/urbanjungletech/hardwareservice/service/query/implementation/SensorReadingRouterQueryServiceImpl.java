package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;
import urbanjungletech.hardwareservice.service.query.SensorReadingRouterQueryService;

@Service
public class SensorReadingRouterQueryServiceImpl implements SensorReadingRouterQueryService {

    private final SensorReadingRouterDAO sensorReadingRouterDAO;
    private final SensorReadingRouterConverter sensorReadingRouterConverter;
    public SensorReadingRouterQueryServiceImpl(SensorReadingRouterDAO sensorReadingRouterDAO,
                                               SensorReadingRouterConverter sensorReadingRouterConverter) {
        this.sensorReadingRouterDAO = sensorReadingRouterDAO;
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
    }
    @Override
    public SensorReadingRouter getSensorReadingRouter(Long id) {
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterDAO.getById(id);
        return this.sensorReadingRouterConverter.toModel(sensorReadingRouterEntity);
    }
}
