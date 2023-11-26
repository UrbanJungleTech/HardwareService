package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

import java.util.Map;

@Service
@Primary
public class ControllerCommunicationServiceProxy implements ControllerCommunicationService {

    Logger logger = LoggerFactory.getLogger(ControllerCommunicationServiceProxy.class);

    private Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices;
    private HardwareControllerQueryService hardwareControllerQueryService;

    public ControllerCommunicationServiceProxy(@Qualifier("ControllerCommunicationServices") Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices,
                                               HardwareControllerQueryService hardwareControllerQueryService) {
        this.controllerCommunicationServices = controllerCommunicationServices;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }
    @Override
    public void sendStateToController(Hardware hardware) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).sendStateToController(hardware);
    }

    @Override
    public void sendInitialState(long hardwareControllerId) {
//        String controllerType = this.hardwareControllerService.getHardwareController(allHardware.get(0).getHardwareControllerId()).getType();
//        this.controllerCommunicationServices.get(controllerType).sendInitialState(allHardware);
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getType();
        return this.controllerCommunicationServices.get(controllerType).getSensorReading(sensor);
    }


    @Override
    public double getAverageSensorReading(String[] sensorPorts) {
//        String controllerType = this.hardwareControllerService.getHardwareControllerBySerialNumber(hardwareControllerSerialNumber).getType();
//        return this.controllerCommunicationServices.get(controllerType).getAverageSensorReading(hardwareControllerSerialNumber, sensorPorts);
        return 1;
    }

    @Override
    public void registerHardware(Hardware hardware) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).registerHardware(hardware);
    }

    @Override
    public void registerSensor(Sensor sensor) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).registerSensor(sensor);
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        this.logger.info("Deregistering hardware: " + hardware.getId());
        String controllerType = this.hardwareControllerQueryService.getHardwareController(hardware.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).deregisterHardware(hardware);
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        String controllerType = this.hardwareControllerQueryService.getHardwareController(sensor.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).deregisterSensor(sensor);
    }
}
