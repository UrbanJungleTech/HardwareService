package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.HardwareControllerGroup;

import java.util.List;

public interface HardwareControllerGroupQueryService {
    HardwareControllerGroup findById(long id);
    List<HardwareControllerGroup> findAll();
}
