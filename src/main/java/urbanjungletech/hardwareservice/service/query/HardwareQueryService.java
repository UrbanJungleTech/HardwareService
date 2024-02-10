package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.List;

public interface HardwareQueryService {
    Hardware getHardware(long hardwareId);
    List<Hardware> getAllHardware();
    Hardware getHardware(String serialNumber, String hardwarePort);
    Hardware getHardwareByDesiredState(long hardwareStateId);
}
