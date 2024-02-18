package urbanjungletech.hardwareservice.service.clientgenerator;

import com.azure.storage.queue.QueueClient;
import urbanjungletech.hardwareservice.model.connectiondetails.AzureConnectionDetails;

public interface AzureQueueClientGenerator {
    QueueClient generateClient(AzureConnectionDetails azureConnectionDetails);
}
