package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;

import java.util.List;

public interface HardwareControllerService {
    HardwareController getHardwareController(long hardwareControllerId);
    List<HardwareController> getAllHardwareControllers();
    List<Hardware> getHardware(long hardwareControllerId);
    List<Sensor> getSensors(long hardwareControllerId);
    String getSerialNumber(long hardwareControllerId);
}
