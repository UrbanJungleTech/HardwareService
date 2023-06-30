package frentz.daniel.hardwareservice.addition;

import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.HardwareController;
import frentz.daniel.hardwareservice.model.Regulator;
import frentz.daniel.hardwareservice.model.Sensor;

public interface HardwareControllerAdditionService extends AdditionService<HardwareController> {
    Hardware addHardware(long hardwareControllerId, Hardware hardware);
    Sensor addSensor(long hardwareControllerId, Sensor sensor);

    void deleteAll();

    Regulator addRegulator(long hardwareControllerId, Regulator regulator);
}
