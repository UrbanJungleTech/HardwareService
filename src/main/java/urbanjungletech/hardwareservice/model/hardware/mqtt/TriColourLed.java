package urbanjungletech.hardwareservice.model.hardware.mqtt;

import urbanjungletech.hardwareservice.model.hardware.Hardware;

public class TriColourLed extends Hardware {
    private MqttPin redPin;
    private MqttPin greenPin;
    private MqttPin bluePin;

    public MqttPin getRedPin() {
        return redPin;
    }

    public void setRedPin(MqttPin redPin) {
        this.redPin = redPin;
    }

    public MqttPin getGreenPin() {
        return greenPin;
    }

    public void setGreenPin(MqttPin greenPin) {
        this.greenPin = greenPin;
    }

    public MqttPin getBluePin() {
        return bluePin;
    }

    public void setBluePin(MqttPin bluePin) {
        this.bluePin = bluePin;
    }
}
