package urbanjungletech.hardwareservice.helpers.services.config;

public class StorageQueueProperties {
    private String key;
    private String queueName;
    private String endpoint;
    private String storageAccountName;
    private String storageAccountKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getStorageAccountName() {
        return storageAccountName;
    }

    public void setStorageAccountName(String storageAccountName) {
        this.storageAccountName = storageAccountName;
    }

    public String getStorageAccountKey() {
        return storageAccountKey;
    }

    public void setStorageAccountKey(String storageAccountKey) {
        this.storageAccountKey = storageAccountKey;
    }
}
