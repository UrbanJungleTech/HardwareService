package frentz.daniel.controller.service;

import frentz.daniel.controllerclient.model.*;

import java.util.List;

public interface HardwareControllerService {
    HardwareController createHardwareController(HardwareController hardwareController);
    Hardware addHardware(long hardwareControllerId, Hardware hardware);
    Sensor addSensor(long hardwareControllerId, Sensor sensor);
    List<HardwareController> getAllHardware();
    HardwareController setHardwareState(long hardwareControllerId, long port, HardwareState state);
    HardwareController getHardwareController(long hardwareControllerId);
    long getHardwareControllerIdFromSerialNumber(String serialNumber);
}
