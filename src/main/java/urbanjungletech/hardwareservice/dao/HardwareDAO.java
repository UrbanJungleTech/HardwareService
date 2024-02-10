package urbanjungletech.hardwareservice.dao;

import urbanjungletech.hardwareservice.entity.hardware.HardwareEntity;
import urbanjungletech.hardwareservice.model.hardware.Hardware;

import java.util.List;

public interface HardwareDAO {
    HardwareEntity createHardware(Hardware hardware);
    HardwareEntity getHardware(long hardwareId);
    HardwareEntity updateHardware(Hardware hardware);
    HardwareEntity getHardware(String serialNumber, String port);
    List<HardwareEntity> getAllHardware();
    void delete(long hardwareId);

    List<HardwareEntity> getHardwareByHardwareControllerId(long hardwareControllerId);
    HardwareEntity getHardwareByStateId(long hardwareStateId);
}
