package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareState;

import java.util.List;

public interface HardwareService {
    Hardware getHardware(long hardwareId);
    List<Hardware> getAllHardware();
    Hardware getHardware(String serialNumber, long hardwarePort);
}
