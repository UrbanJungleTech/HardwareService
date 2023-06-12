package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.HardwareController;
import frentz.daniel.hardwareservice.client.model.Sensor;

public interface HardwareControllerAdditionService extends AdditionService<HardwareController> {
    Hardware addHardware(long hardwareControllerId, Hardware hardware);
    Sensor addSensor(long hardwareControllerId, Sensor sensor);
}
