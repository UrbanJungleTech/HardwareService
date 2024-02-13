package urbanjungletech.hardwareservice.addition.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.addition.HardwareControllerAdditionService;
import urbanjungletech.hardwareservice.addition.HardwareControllerGroupAdditionService;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerGroupDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.List;
import java.util.Optional;

@Service
public class HardwareControllerGroupAdditionServiceImpl implements HardwareControllerGroupAdditionService {

    private final HardwareControllerGroupDAO hardwareControllerGroupDAO;
    private final HardwareControllerGroupConverter hardwareControllerGroupConverter;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    private final HardwareControllerAdditionService hardwareControllerAdditionService;

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
