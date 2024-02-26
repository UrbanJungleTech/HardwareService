package urbanjungletech.hardwareservice.addition.implementation.hardware;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.dao.HardwareDAO;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;
import urbanjungletech.hardwareservice.model.hardware.mqtt.SimpleHardware;
import urbanjungletech.hardwareservice.repository.HardwareRepository;

@Service
public class SimpleHardwareAdditionService implements SpecificAdditionService<SimpleHardware>{
    private MqttPinAdditionService mqttPinAdditionService;
    private HardwareDAO hardwareDAO;
    public SimpleHardwareAdditionService(MqttPinAdditionService mqttPinAdditionService,
                                         HardwareDAO hardwareDAO) {
        this.mqttPinAdditionService = mqttPinAdditionService;
        this.hardwareDAO = hardwareDAO;
    }
    @Override
    public void create(SimpleHardware simpleHardware) {
        MqttPin mqttPin = mqttPinAdditionService.create(simpleHardware.getPin());
        simpleHardware.setPin(mqttPin);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(long id, SimpleHardware routerModel) {

    }
}
