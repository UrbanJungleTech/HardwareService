package urbanjungletech.hardwareservice.service;

import urbanjungletech.hardwareservice.model.Hardware;

import java.util.List;

public interface HardwareService {
    Hardware getHardware(long hardwareId);
    List<Hardware> getAllHardware();
    Hardware getHardware(String serialNumber, long hardwarePort);
    Hardware getHardwareByDesiredState(long hardwareStateId);
}
