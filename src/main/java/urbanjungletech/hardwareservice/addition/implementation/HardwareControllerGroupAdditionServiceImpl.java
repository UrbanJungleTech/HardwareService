package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.HardwareControllerGroupAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerGroupDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareControllerGroupAdditionServiceImpl implements HardwareControllerGroupAdditionService {

    private HardwareControllerGroupDAO hardwareControllerGroupDAO;
    private HardwareControllerGroupConverter hardwareControllerGroupConverter;

    public HardwareControllerGroupAdditionServiceImpl(HardwareControllerGroupDAO hardwareControllerGroupDAO,
                                                      HardwareControllerGroupConverter hardwareControllerGroupConverter) {
        this.hardwareControllerGroupDAO = hardwareControllerGroupDAO;
        this.hardwareControllerGroupConverter = hardwareControllerGroupConverter;
    }

    @Override
    public HardwareControllerGroup create(HardwareControllerGroup hardwareControllerGroup) {
        HardwareControllerGroupEntity createdEntity = this.hardwareControllerGroupDAO.createHardwareControllerGroup(hardwareControllerGroup);
        createdEntity = this.hardwareControllerGroupDAO.findById(createdEntity.getId());
        return this.hardwareControllerGroupConverter.toModel(createdEntity);
    }

    @Override
    public void delete(long id) {
        this.hardwareControllerGroupDAO.delete(id);
    }

    @Override
    public HardwareControllerGroup update(long id, HardwareControllerGroup hardwareControllerGroup) {
        return null;
    }

    @Override
    public List<HardwareControllerGroup> updateList(List<HardwareControllerGroup> models) {
        return null;
    }
}
