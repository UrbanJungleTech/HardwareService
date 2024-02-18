package urbanjungletech.hardwareservice.model.connectiondetails;

public class KafkaConnectionDetails {
    private String url;
    private String topic;

    public KafkaConnectionDetails(String url, String topic) {
        this.url = url;
        this.topic = topic;
    }

    public String getUrl() {
        return url;
    }

    public String getTopic() {
        return topic;
    }
}
