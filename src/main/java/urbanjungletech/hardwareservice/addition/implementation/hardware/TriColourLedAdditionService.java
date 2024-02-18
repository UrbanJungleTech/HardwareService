package urbanjungletech.hardwareservice.addition.implementation.hardware;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.implementation.TriColourLedConverter;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;
import urbanjungletech.hardwareservice.model.hardware.mqtt.TriColourLed;

@Service
public class TriColourLedAdditionService implements SpecificAdditionService<TriColourLed> {
    private final TriColourLedConverter triColourLedConverter;
    private MqttPinAdditionService mqttPinAdditionService;

    public TriColourLedAdditionService(TriColourLedConverter triColourLedConverter, MqttPinAdditionService mqttPinAdditionService) {
        this.triColourLedConverter = triColourLedConverter;
        this.mqttPinAdditionService = mqttPinAdditionService;
    }

    @Override
    public void create(TriColourLed triColourLed) {
        MqttPin blueLed = this.mqttPinAdditionService.create(triColourLed.getBluePin());
        triColourLed.setBluePin(blueLed);
        MqttPin greenLed = this.mqttPinAdditionService.create(triColourLed.getGreenPin());
        triColourLed.setGreenPin(greenLed);
        MqttPin redLed = this.mqttPinAdditionService.create(triColourLed.getRedPin());
        triColourLed.setRedPin(redLed);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(long id, TriColourLed routerModel) {

    }
}
