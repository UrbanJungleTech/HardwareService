package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service;

import urbanjungletech.hardwareservice.exception.exception.DeviceNotFoundException;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

public interface TplinkActionService {
    void setState(Hardware hardware) throws DeviceNotFoundException;
}
