package urbanjungletech.hardwareservice.dao.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardware.mqtt.MqttPinConverter;
import urbanjungletech.hardwareservice.dao.MqttPinDAO;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.MqttPinEntity;
import urbanjungletech.hardwareservice.entity.hardware.mqtt.SimpleHardwareEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.hardware.mqtt.MqttPin;
import urbanjungletech.hardwareservice.repository.HardwareRepository;
import urbanjungletech.hardwareservice.repository.MqttPinRepository;

@Service
public class MqttPinDAOImpl implements MqttPinDAO {

    private final MqttPinRepository mqttPinRepository;
    private final MqttPinConverter mqttPinConverter;

    private final ExceptionService exceptionService;
    private final HardwareRepository hardwareRepository;
    public MqttPinDAOImpl(MqttPinRepository mqttPinRepository,
                          MqttPinConverter mqttPinConverter,
                          ExceptionService exceptionService,
                          HardwareRepository hardwareRepository) {
        this.mqttPinRepository = mqttPinRepository;
        this.mqttPinConverter = mqttPinConverter;
        this.exceptionService = exceptionService;
        this.hardwareRepository = hardwareRepository;
    }
    @Override
    public MqttPinEntity createMqttPin(MqttPin mqttPin) {
        MqttPinEntity mqttPinEntity = new MqttPinEntity();
        this.mqttPinConverter.fillEntity(mqttPinEntity, mqttPin);
        MqttPinEntity result = this.mqttPinRepository.save(mqttPinEntity);
        return result;
    }

    @Override
    public void deleteMqttPin(Long id) {
        this.mqttPinRepository.deleteById(id);
    }

    @Override
    public MqttPinEntity updateMqttPin(MqttPin mqttPin) {
        MqttPinEntity mqttPinEntity = this.mqttPinRepository.findById(mqttPin.getId())
                .orElseThrow(() -> this.exceptionService.createNotFoundException(MqttPin.class, mqttPin.getId()));
        this.mqttPinConverter.fillEntity(mqttPinEntity, mqttPin);
        MqttPinEntity result = this.mqttPinRepository.save(mqttPinEntity);
        return result;
    }

    @Override
    public MqttPinEntity findById(Long id) {
        return this.mqttPinRepository.findById(id)
                .orElseThrow(() -> this.exceptionService.createNotFoundException(MqttPin.class, id));
    }
}
