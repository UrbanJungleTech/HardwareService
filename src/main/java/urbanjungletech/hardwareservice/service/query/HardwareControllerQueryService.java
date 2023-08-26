package urbanjungletech.hardwareservice.service.query;

import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.HardwareController;
import urbanjungletech.hardwareservice.model.Sensor;

import java.util.List;

public interface HardwareControllerQueryService {
    HardwareController getHardwareController(long hardwareControllerId);
    List<HardwareController> getAllHardwareControllers();
    List<Hardware> getHardware(long hardwareControllerId);
    List<Sensor> getSensors(long hardwareControllerId);
    String getSerialNumber(long hardwareControllerId);
    HardwareController getHardwareControllerBySerialNumber(String serialNumber);
}
