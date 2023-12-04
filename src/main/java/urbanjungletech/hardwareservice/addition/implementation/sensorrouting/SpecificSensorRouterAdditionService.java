package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.SensorReadingRouter;

public interface SpecificSensorRouterAdditionService <RouterModel extends SensorReadingRouter> {
    RouterModel create(RouterModel routerModel);
    void delete(long id);
    RouterModel update(long id, RouterModel routerModel);
}
