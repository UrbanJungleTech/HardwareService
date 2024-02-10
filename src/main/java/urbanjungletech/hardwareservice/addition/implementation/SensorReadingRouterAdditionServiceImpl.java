package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.SensorReadingRouterAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.sensorreadingrouter.SensorReadingRouterConverter;
import urbanjungletech.hardwareservice.dao.SensorReadingRouterDAO;
import urbanjungletech.hardwareservice.entity.sensorreadingrouter.SensorReadingRouterEntity;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SensorReadingRouterAdditionServiceImpl implements SensorReadingRouterAdditionService {

    private final SensorReadingRouterDAO sensorReadingRouterDAO;
    private final SensorReadingRouterConverter sensorReadingRouterConverter;
    private final Map<Class, SpecificAdditionService> additionServices;
    public SensorReadingRouterAdditionServiceImpl(SensorReadingRouterDAO sensorReadingRouterDAO,
                                                  SensorReadingRouterConverter sensorReadingRouterConverter,
                                                  Map<Class, SpecificAdditionService> additionServices){

        this.sensorReadingRouterDAO = sensorReadingRouterDAO;
        this.sensorReadingRouterConverter = sensorReadingRouterConverter;
        this.additionServices = additionServices;
    }

    @Override
    public SensorReadingRouter create(SensorReadingRouter sensorReadingRouter) {
        if(this.additionServices.containsKey(sensorReadingRouter.getClass())){
            this.additionServices.get(sensorReadingRouter.getClass()).create(sensorReadingRouter);
        }
        SensorReadingRouterEntity sensorReadingRouterEntity = this.sensorReadingRouterDAO.create(sensorReadingRouter);
        SensorReadingRouter result = this.sensorReadingRouterConverter.toModel(sensorReadingRouterEntity);
        return result;
    }

    @Override
    public void delete(long id) {
        this.sensorReadingRouterDAO.delete(id);
    }

    @Override
    public SensorReadingRouter update(long id, SensorReadingRouter sensorReadingRouter) {
        SensorReadingRouterEntity result = this.sensorReadingRouterDAO.update(sensorReadingRouter);
        return this.sensorReadingRouterConverter.toModel(result);
    }

    @Override
    public List<SensorReadingRouter> updateList(List<SensorReadingRouter> models) {
        List<SensorReadingRouter> result = new ArrayList<>();
        models.forEach(model -> {
            if (model.getId() != null) {
                result.add(this.update(model.getId(), model));
            } else {
                result.add(this.create(model));
            }
        });
        return result;
    }
}
