package urbanjungletech.hardwareservice.model;

public class MqttHardwareControllerConfiguration extends HardwareControllerConfiguration {
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
