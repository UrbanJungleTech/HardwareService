package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
@Primary
public class ControllerCommunicationServiceProxy implements ControllerCommunicationService {

    Logger logger = LoggerFactory.getLogger(ControllerCommunicationServiceProxy.class);

    private final Map<Class <? extends HardwareController>, SpecificControllerCommunicationService> controllerCommunicationServices;
    private final HardwareControllerQueryService hardwareControllerQueryService;

    public ControllerCommunicationServiceProxy(Map<Class <? extends HardwareController>, SpecificControllerCommunicationService> controllerCommunicationServices,
                                               HardwareControllerQueryService hardwareControllerQueryService) {
        this.controllerCommunicationServices = controllerCommunicationServices;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }
    @Override
    public void sendStateToController(Hardware hardware) {
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getClass();
        this.controllerCommunicationServices.get(controllerType).sendStateToController(hardware);
    }

    @Override
    public void sendInitialState(long hardwareControllerId) {
//        String controllerType = this.hardwareControllerService.getHardwareController(allHardware.get(0).getHardwareControllerId()).getType();
//        this.controllerCommunicationServices.get(controllerType).sendInitialState(allHardware);
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        Class<? extends HardwareController> controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getClass();
        return this.controllerCommunicationServices.get(controllerType).getSensorReading(sensor);
    }


    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
//        Class controllerType = this.hardwareControllerService.getHardwareControllerBySerialNumber(hardwareControllerSerialNumber).getType();
//        return this.controllerCommunicationServices.get(controllerType).getAverageSensorReading(hardwareControllerSerialNumber, sensorPorts);
        return 1;
    }

    @Override
    public void registerHardware(Hardware hardware) {
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getClass();
        this.controllerCommunicationServices.get(controllerType).registerHardware(hardware);
    }

    @Override
    public void registerSensor(Sensor sensor) {
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getClass();
        this.controllerCommunicationServices.get(controllerType).registerSensor(sensor);
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        this.logger.info("Deregistering hardware: " + hardware.getId());
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getClass();
        this.controllerCommunicationServices.get(controllerType).deregisterHardware(hardware);
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        Class controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getClass();
        this.controllerCommunicationServices.get(controllerType).deregisterSensor(sensor);
    }
}
