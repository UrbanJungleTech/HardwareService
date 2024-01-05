package urbanjungletech.hardwareservice.addition.implementation.hardwarecontroller;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareMqttClientAdditionService;
import urbanjungletech.hardwareservice.addition.implementation.sensorrouting.SpecificAdditionService;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;
import urbanjungletech.hardwareservice.model.hardwarecontroller.MqttHardwareController;

@Service
public class MqttHardwareControllerAdditionService implements SpecificAdditionService<MqttHardwareController> {

    private final HardwareMqttClientAdditionService hardwareMqttClientAdditionService;

    public MqttHardwareControllerAdditionService(HardwareMqttClientAdditionService hardwareMqttClientAdditionService){
        this.hardwareMqttClientAdditionService = hardwareMqttClientAdditionService;
    }
    @Override
    public void create(MqttHardwareController mqttHardwareController) {
        mqttHardwareController.getHardwareMqttClient().setHardwareControllerId(mqttHardwareController.getId());
        HardwareMqttClient hardwareMqttClient = this.hardwareMqttClientAdditionService.create(mqttHardwareController.getHardwareMqttClient());
        mqttHardwareController.setHardwareMqttClient(hardwareMqttClient);
    }

    @Override
    public void delete(long id) {
        this.hardwareMqttClientAdditionService.delete(id);
    }

    @Override
    public void update(long id, MqttHardwareController routerModel) {
        this.hardwareMqttClientAdditionService.update(id, routerModel.getHardwareMqttClient());
    }
}
