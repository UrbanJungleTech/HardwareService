package urbanjungletech.hardwareservice.addition.implementation.hardware;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.dao.MqttPinDAO;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;

import java.util.List;

@Service
public class MqttPinAdditionServiceImpl implements MqttPinAdditionService{

    private final MqttPinDAO mqttPinDAO;
    private final MqttPinConverter mqttPinConverter;

    public MqttPinAdditionServiceImpl(MqttPinDAO mqttPinDAO, MqttPinConverter mqttPinConverter) {
        this.mqttPinDAO = mqttPinDAO;
        this.mqttPinConverter = mqttPinConverter;
    }
    @Override
    public MqttPin create(MqttPin mqttPin) {
        MqttPinEntity mqttPinEntity = this.mqttPinDAO.createMqttPin(mqttPin);
        return this.mqttPinConverter.toModel(mqttPinEntity);
    }

    @Override
    public void delete(long id) {
        this.mqttPinDAO.deleteMqttPin(id);
    }

    @Override
    public MqttPin update(long id, MqttPin mqttPin) {
        mqttPin.setId(id);
        this.mqttPinDAO.updateMqttPin(mqttPin);
        return mqttPin;
    }

    @Override
    public List<MqttPin> updateList(List<MqttPin> models) {
        return null;
    }
}
