package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.Hardware;

import java.util.List;

public interface HardwareService {
    public Hardware getHardware(long hardwareId);
    public List<Hardware> getAllHardware();
    Hardware getHardware(String serialNumber, long hardwarePort);
}
