package urbanjungletech.hardwareservice.converter.implementation;

import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareControllerGroupConverterImpl implements HardwareControllerGroupConverter {

    private HardwareControllerConverter hardwareControllerConverter;

    public HardwareControllerGroupConverterImpl(HardwareControllerConverter hardwareControllerConverter) {
        this.hardwareControllerConverter = hardwareControllerConverter;
    }
    @Override
    public HardwareControllerGroup toModel(HardwareControllerGroupEntity hardwareControllerGroupEntity) {
        HardwareControllerGroup result = new HardwareControllerGroup();
        result.setId(hardwareControllerGroupEntity.getId());
        result.setName(hardwareControllerGroupEntity.getName());
        List<HardwareController> hardwareControllers = hardwareControllerConverter.toModels(hardwareControllerGroupEntity.getHardwareControllers());
        result.setHardwareControllers(hardwareControllers);
        return result;
    }

    @Override
    public void fillEntity(HardwareControllerGroupEntity hardwareControllerGroupEntity, HardwareControllerGroup hardwareControllerGroup) {
        hardwareControllerGroupEntity.setName(hardwareControllerGroup.getName());
    }
}
