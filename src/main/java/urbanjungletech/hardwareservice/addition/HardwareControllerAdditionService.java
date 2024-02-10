package urbanjungletech.hardwareservice.addition;

import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

public interface HardwareControllerAdditionService extends AdditionService<HardwareController> {
    Hardware addHardware(long hardwareControllerId, Hardware hardware);
    Sensor addSensor(long hardwareControllerId, Sensor sensor);
    void deleteAll();
}
