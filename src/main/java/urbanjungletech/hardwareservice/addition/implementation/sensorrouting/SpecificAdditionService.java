package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

public interface SpecificAdditionService <Model> {
    void create(Model routerModel);
    void delete(long id);
    void update(long id, Model routerModel);
}
