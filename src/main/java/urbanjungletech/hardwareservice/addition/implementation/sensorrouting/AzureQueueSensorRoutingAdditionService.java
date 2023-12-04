package urbanjungletech.hardwareservice.addition.implementation.sensorrouting;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.AdditionService;
import urbanjungletech.hardwareservice.addition.CredentialsAdditionService;
import urbanjungletech.hardwareservice.model.credentials.Credentials;
import urbanjungletech.hardwareservice.model.sensorreadingrouter.AzureQueueSensorReadingRouter;

import java.util.List;

@Service
public class AzureQueueSensorRoutingAdditionService implements SpecificSensorRouterAdditionService<AzureQueueSensorReadingRouter> {

    private final CredentialsAdditionService credentialsAdditionService;

    public AzureQueueSensorRoutingAdditionService(CredentialsAdditionService credentialsAdditionService){
        this.credentialsAdditionService = credentialsAdditionService;
    }

    @Override
    public AzureQueueSensorReadingRouter create(AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        Credentials credentials = this.credentialsAdditionService.create(azureQueueSensorReadingRouter.getCredentials());
        azureQueueSensorReadingRouter.setCredentials(credentials);
        return azureQueueSensorReadingRouter;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public AzureQueueSensorReadingRouter update(long id, AzureQueueSensorReadingRouter azureQueueSensorReadingRouter) {
        return null;
    }
}
