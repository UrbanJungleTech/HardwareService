package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.connectiondetails.AzureConnectionDetailsAdditionService;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;

@Service
public class AzureQueueSensorRouterAdditionService implements SpecificAdditionService<AzureQueueSensorReadingRouter> {

    private final AzureConnectionDetailsAdditionService azureConnectionDetailsAdditionService;


    public AzureQueueSensorRouterAdditionService(AzureConnectionDetailsAdditionService azureConnectionDetailsAdditionService){
        this.azureConnectionDetailsAdditionService = azureConnectionDetailsAdditionService;
    }

    @Override
    public void create(AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        AzureConnectionDetails azureConnectionDetails = this.azureConnectionDetailsAdditionService.create(azureQueueSensorReadingRouter.getAzureConnectionDetails());
        azureQueueSensorReadingRouter.setAzureConnectionDetails(azureConnectionDetails);
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public void update(long id, AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        this.azureConnectionDetailsAdditionService.update(id, azureQueueSensorReadingRouter.getAzureConnectionDetails());
    }
}
