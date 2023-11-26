package urbanjungletech.hardwareservice.service.query.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.dao.HardwareControllerGroupDAO;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import urbanjungletech.hardwareservice.service.query.HardwareControllerGroupQueryService;

import java.util.List;

@Service
public class HardwareControllerGroupQueryServiceImpl implements HardwareControllerGroupQueryService {

    private final HardwareControllerGroupDAO hardwareControllerGroupDAO;
    private final HardwareControllerGroupConverter hardwareControllerGroupConverter;
    public HardwareControllerGroupQueryServiceImpl(HardwareControllerGroupDAO hardwareControllerGroupDAO,
                                                   HardwareControllerGroupConverter hardwareControllerGroupConverter) {
        this.hardwareControllerGroupDAO = hardwareControllerGroupDAO;
        this.hardwareControllerGroupConverter = hardwareControllerGroupConverter;
    }
    @Override
    public HardwareControllerGroup findById(long id) {
        HardwareControllerGroupEntity result = this.hardwareControllerGroupDAO.findById(id);
        return this.hardwareControllerGroupConverter.toModel(result);
    }

    @Override
    public List<HardwareControllerGroup> findAll() {
        List<HardwareControllerGroupEntity> result = this.hardwareControllerGroupDAO.getAllHardwareControllers();
        return result.stream().map(this.hardwareControllerGroupConverter::toModel).toList();
    }
}
