package urbanjungletech.hardwareservice.service.tplink;

import urbanjungletech.hardwareservice.exception.DeviceNotFoundException;
import urbanjungletech.hardwareservice.model.Hardware;

public interface TplinkActionService {
    void setState(Hardware hardware) throws DeviceNotFoundException;
}
