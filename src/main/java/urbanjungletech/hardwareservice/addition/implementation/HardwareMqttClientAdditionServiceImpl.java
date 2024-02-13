package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareMqttClientAdditionService;
import urbanjungletech.hardwareservice.converter.hardwarecontroller.HardwareMqttClientConverter;
import urbanjungletech.hardwareservice.dao.HardwareMqttClientDAO;
import urbanjungletech.hardwareservice.entity.hardwarecontroller.HardwareMqttClientEntity;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareMqttClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HardwareMqttClientAdditionServiceImpl implements HardwareMqttClientAdditionService {
    private final HardwareMqttClientDAO hardwareMqttClientDAO;
    private final HardwareMqttClientConverter hardwareMqttClientConverter;
    public HardwareMqttClientAdditionServiceImpl(HardwareMqttClientDAO hardwareMqttClientDAO,
                                                 HardwareMqttClientConverter hardwareMqttClientConverter){
        this.hardwareMqttClientDAO = hardwareMqttClientDAO;
        this.hardwareMqttClientConverter = hardwareMqttClientConverter;
    }

    @Override
    public HardwareMqttClient create(HardwareMqttClient hardwareMqttClient) {
        HardwareMqttClientEntity hardwareMqttClientEntity = this.hardwareMqttClientDAO.create(hardwareMqttClient);
        return this.hardwareMqttClientConverter.toModel(hardwareMqttClientEntity);
    }

    @Override
    public void delete(long id) {
        this.hardwareMqttClientDAO.delete(id);
    }

    @Override
    public HardwareMqttClient update(long id, HardwareMqttClient hardwareMqttClient) {
        HardwareMqttClientEntity hardwareMqttClientEntity = this.hardwareMqttClientDAO.update(hardwareMqttClient);
        return this.hardwareMqttClientConverter.toModel(hardwareMqttClientEntity);
    }

    @Override
    public List<HardwareMqttClient> updateList(List<HardwareMqttClient> models) {
        models.stream().map((model) -> {
            if (model.getId() == null) {
                return this.create(model);
            } else {
                return this.update(model.getId(), model);
            }
        }).collect(Collectors.toList());
        return models;
    }
}
