package urbanjungletech.hardwareservice.model.hardware.mqtt;

public class CommonPinStatefulHardware extends StatefulHardware{
    private MqttPin commonPin;
    private CommonPinType commonPinType;

    public MqttPin getCommonPin() {
        return commonPin;
    }

    public CommonPinType getCommonPinType() {
        return commonPinType;
    }

    public void setCommonPinType(CommonPinType commonPinType) {
        this.commonPinType = commonPinType;
    }

    public void setCommonPin(MqttPin commonPin) {
        this.commonPin = commonPin;
    }
}
