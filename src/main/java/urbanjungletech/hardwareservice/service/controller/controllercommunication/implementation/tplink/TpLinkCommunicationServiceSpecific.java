package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.TpLinkHardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.SpecificControllerCommunicationService;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TplinkActionService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

@Service()
public class TpLinkCommunicationServiceSpecific implements SpecificControllerCommunicationService<TpLinkHardwareController> {

    private final TplinkActionService tplinkActionService;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    public TpLinkCommunicationServiceSpecific(TplinkActionService tplinkActionService,
                                              HardwareControllerQueryService hardwareControllerQueryService) {
        this.tplinkActionService = tplinkActionService;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }
    @Override
    public void sendStateToController(Hardware hardware) {
        this.tplinkActionService.setState(hardware);
    }

    @Override
    public void sendInitialState(long hardwareControllerId) {

    }

    @Override
    public double getSensorReading(Sensor sensor) {
        return 0;
    }

    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
        return 0;
    }

    @Override
    public void registerHardware(Hardware hardware) {
        this.tplinkActionService.setState(hardware);
    }

    @Override
    public void registerSensor(Sensor sensor) {

    }

    @Override
    public void deregisterHardware(Hardware hardware) {

    }

    @Override
    public void deregisterSensor(Sensor sensor) {

    }
}
