package frentz.daniel.hardwareservice.dao;

import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.model.Hardware;

import java.util.List;

public interface HardwareDAO {
    HardwareEntity createHardware(Hardware hardware);
    HardwareEntity getHardware(long hardwareId);
    HardwareEntity updateHardware(Hardware hardware);
    HardwareEntity updateHardware(HardwareEntity hardware);
    HardwareEntity getHardware(String serialNumber, long port);
    List<HardwareEntity> getAllHardware();
    void delete(long hardwareId);

    List<HardwareEntity> getHardwareByHardwareControllerId(long hardwareControllerId);
    HardwareEntity getHardwareByStateId(long hardwareStateId);
}
