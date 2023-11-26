package urbanjungletech.hardwareservice.converter.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.converter.HardwareControllerConverter;
import urbanjungletech.hardwareservice.converter.HardwareControllerGroupConverter;
import urbanjungletech.hardwareservice.entity.HardwareControllerGroupEntity;
import urbanjungletech.hardwareservice.model.HardwareControllerGroup;

import java.util.List;
import java.util.Optional;

@Service
public class HardwareControllerGroupConverterImpl implements HardwareControllerGroupConverter {

    private final HardwareControllerConverter hardwareControllerConverter;

    public HardwareControllerGroupConverterImpl(HardwareControllerConverter hardwareControllerConverter) {
        this.hardwareControllerConverter = hardwareControllerConverter;
    }

    @Override
    public HardwareControllerGroup toModel(HardwareControllerGroupEntity hardwareControllerGroupEntity) {
        HardwareControllerGroup result = new HardwareControllerGroup();
        result.setId(hardwareControllerGroupEntity.getId());
        result.setName(hardwareControllerGroupEntity.getName());
        Optional.ofNullable(hardwareControllerGroupEntity.getHardwareControllers()).ifPresent((hardwareControllers) -> {
            List<Long> hardwareControllersIds = hardwareControllers.stream()
                    .map((hardwareControllerEntity -> hardwareControllerEntity.getId())).toList();
            result.setHardwareControllers(hardwareControllersIds);
        });
        return result;
    }

    @Override
    public void fillEntity(HardwareControllerGroupEntity hardwareControllerGroupEntity, HardwareControllerGroup hardwareControllerGroup) {
        hardwareControllerGroupEntity.setName(hardwareControllerGroup.getName());
    }
}
