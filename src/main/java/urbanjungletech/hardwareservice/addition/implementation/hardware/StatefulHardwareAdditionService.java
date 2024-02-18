package urbanjungletech.hardwareservice.addition.implementation.hardware;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.dao.MqttPinDAO;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.StatefulHardware;

@Service
public class StatefulHardwareAdditionService implements SpecificAdditionService<StatefulHardware> {

    private final MqttPinDAO mqttPinDAO;
    private final MqttPinConverter mqttPinConverter;

    public StatefulHardwareAdditionService(MqttPinDAO mqttPinDAO, MqttPinConverter mqttPinConverter) {
        this.mqttPinDAO = mqttPinDAO;
        this.mqttPinConverter = mqttPinConverter;
    }

    @Override
    public void create(StatefulHardware statefulHardware) {
        statefulHardware.getPins().forEach((key, value) -> {
            MqttPinEntity mqttPinEntity = this.mqttPinDAO.createMqttPin(value);
            value.setId(mqttPinEntity.getId());
        });
    }

    @Override
    public void delete(long id) {
        this.mqttPinDAO.deleteMqttPin(id);
    }

    @Override
    public void update(long id, StatefulHardware routerModel) {
        this.mqttPinDAO.updateMqttPin(routerModel.getPins().get(id));
    }
}
