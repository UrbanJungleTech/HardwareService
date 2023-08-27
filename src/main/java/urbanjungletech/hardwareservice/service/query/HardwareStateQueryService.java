package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.HardwareState;

public interface HardwareStateQueryService {
    HardwareState getHardwareStateById(long hardwareStateId);
}
