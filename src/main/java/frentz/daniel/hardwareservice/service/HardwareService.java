package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareState;

import java.util.List;

public interface HardwareService {
    Hardware getHardware(long hardwareId);
    List<Hardware> getAllHardware();
    Hardware getHardware(String serialNumber, long hardwarePort);
}
