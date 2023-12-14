package urbanjungletech.hardwareservice.helpers.services.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "development.azure")
public class AzureProperties {
    private StorageQueueProperties storageQueue;

    public StorageQueueProperties getStorageQueue() {
        return storageQueue;
    }

    public void setStorageQueue(StorageQueueProperties storageQueue) {
        this.storageQueue = storageQueue;
    }
}
