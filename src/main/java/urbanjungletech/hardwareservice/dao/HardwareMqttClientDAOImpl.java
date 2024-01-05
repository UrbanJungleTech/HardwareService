package urbanjungletech.hardwareservice.dao;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.HardwareMqttClientConverter;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareControllerEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.MqttHardwareControllerEntity;
import urbanjungletech.hardwareservice.exception.service.ExceptionService;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;
import urbanjungletech.hardwareservice.repository.HardwareControllerRepository;
import urbanjungletech.hardwareservice.repository.HardwareMqttClientRepository;

@Service
public class HardwareMqttClientDAOImpl implements HardwareMqttClientDAO{

    private final HardwareMqttClientRepository hardwareMqttClientRepository;
    private final HardwareControllerRepository hardwareControllerRepository;
    private final ExceptionService exceptionService;
    private final HardwareMqttClientConverter hardwareMqttClientConverter;
    public HardwareMqttClientDAOImpl(HardwareMqttClientRepository hardwareMqttClientRepository,
                                     HardwareControllerRepository hardwareControllerRepository,
                                     ExceptionService exceptionService,
                                     HardwareMqttClientConverter hardwareMqttClientConverter){
        this.hardwareMqttClientRepository = hardwareMqttClientRepository;
        this.hardwareControllerRepository = hardwareControllerRepository;
        this.exceptionService = exceptionService;
        this.hardwareMqttClientConverter = hardwareMqttClientConverter;
    }
    @Override
    public HardwareMqttClientEntity create(HardwareMqttClient hardwareMqttClient) {
        MqttHardwareControllerEntity hardwareControllerEntity = (MqttHardwareControllerEntity)this.hardwareControllerRepository.findById(hardwareMqttClient.getHardwareControllerId())
                .orElseThrow(() -> exceptionService.createNotFoundException(HardwareController.class, hardwareMqttClient.getId()));
        HardwareMqttClientEntity hardwareMqttClientEntity = new HardwareMqttClientEntity();
        this.hardwareMqttClientConverter.fillEntity(hardwareMqttClientEntity, hardwareMqttClient);
        HardwareMqttClientEntity result = this.hardwareMqttClientRepository.save(hardwareMqttClientEntity);
        hardwareControllerEntity.setHardwareMqttClient(hardwareMqttClientEntity);
        this.hardwareControllerRepository.save(hardwareControllerEntity);
        return result;
    }

    @Override
    public void delete(long id) {
        MqttHardwareControllerEntity hardwareController = (MqttHardwareControllerEntity) this.hardwareControllerRepository.findById(id)
                .orElseThrow(() -> exceptionService.createNotFoundException(HardwareMqttClient.class, id));
        this.hardwareMqttClientRepository.deleteById(id);
        hardwareController.setHardwareMqttClient(null);
        this.hardwareControllerRepository.save(hardwareController);
    }

    @Override
    public HardwareMqttClientEntity update(HardwareMqttClient hardwareMqttClient) {
        return null;
    }
}
