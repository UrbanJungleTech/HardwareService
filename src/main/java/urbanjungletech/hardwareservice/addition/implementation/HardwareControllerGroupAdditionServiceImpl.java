package urbanjungletech.hardwareservice.addition.implementation;

import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerGroupAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerGroupDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.entity.HardwareEntity;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.List;
import java.util.Optional;

@Service
public class HardwareControllerGroupAdditionServiceImpl implements HardwareControllerGroupAdditionService {

    private HardwareControllerGroupDAO hardwareControllerGroupDAO;
    private HardwareControllerGroupConverter hardwareControllerGroupConverter;
    private HardwareControllerQueryService hardwareControllerQueryService;
    private HardwareControllerAdditionService hardwareControllerAdditionService;

    public HardwareControllerGroupAdditionServiceImpl(HardwareControllerGroupDAO hardwareControllerGroupDAO,
                                                      HardwareControllerGroupConverter hardwareControllerGroupConverter,
                                                      HardwareControllerQueryService hardwareControllerQueryService,
                                                      HardwareControllerAdditionService hardwareControllerAdditionService) {
        this.hardwareControllerGroupDAO = hardwareControllerGroupDAO;
        this.hardwareControllerGroupConverter = hardwareControllerGroupConverter;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.hardwareControllerAdditionService = hardwareControllerAdditionService;
    }

    @Override
    public HardwareControllerGroup create(HardwareControllerGroup hardwareControllerGroup) {
        final HardwareControllerGroupEntity createdEntity = this.hardwareControllerGroupDAO.createHardwareControllerGroup(hardwareControllerGroup);

        Optional.ofNullable(hardwareControllerGroup.getHardwareControllers()).ifPresent((hardwareControllers) -> hardwareControllers.stream().map((hardwareControllerId) -> {
            final HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(hardwareControllerId);
            hardwareController.setHardwareControllerGroupId(createdEntity.getId());
            this.hardwareControllerAdditionService.update(hardwareControllerId, hardwareController);
            return hardwareControllerId;
        }).toList());
        HardwareControllerGroupEntity resultEntity = this.hardwareControllerGroupDAO.findById(createdEntity.getId());
        return this.hardwareControllerGroupConverter.toModel(resultEntity);
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
