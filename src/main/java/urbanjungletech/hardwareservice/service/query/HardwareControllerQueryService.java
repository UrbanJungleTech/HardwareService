package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;

import java.util.List;

public interface HardwareControllerQueryService {
    HardwareController getHardwareController(long hardwareControllerId);
    List<HardwareController> getAllHardwareControllers();
    List<Hardware> getHardware(long hardwareControllerId);
    List<Sensor> getSensors(long hardwareControllerId);
    String getSerialNumber(long hardwareControllerId);
    HardwareController getHardwareControllerBySerialNumber(String serialNumber);
}
