package frentz.daniel.hardwareservice.service.controllercommunication.implementation;

import frentz.daniel.hardwareservice.config.mqtt.listener.MicrocontrollerMessageListener;
import frentz.daniel.hardwareservice.model.Hardware;
import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Primary
public class ControllerCommunicationServiceProxy implements ControllerCommunicationService {

    Logger logger = LoggerFactory.getLogger(ControllerCommunicationServiceProxy.class);

    private Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices;
    private HardwareControllerService hardwareControllerService;

    public ControllerCommunicationServiceProxy(@Qualifier("ControllerCommunicationServices") Map<String, ControllerCommunicationServiceImplementation> controllerCommunicationServices,
                                               HardwareControllerService hardwareControllerService) {
        this.controllerCommunicationServices = controllerCommunicationServices;
        this.hardwareControllerService = hardwareControllerService;
    }
    @Override
    public void sendStateToController(Hardware hardware) {
        String controllerType = this.hardwareControllerService.getHardwareController(hardware.getHardwareControllerId()).getType();
        this.controllerCommunicationServices.get(controllerType).sendStateToController(hardware);
    }

    @Override
    public void sendInitialState(String serialNumber, List<Hardware> allHardware) {
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        this.controllerCommunicationServices.get(controllerType).sendInitialState(serialNumber, allHardware);
    }

    @Override
    public double getSensorReading(Sensor sensor) {
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        return this.controllerCommunicationServices.get(controllerType).getSensorReading(sensor);
    }


    @Override
    public double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts) {
        String controllerType = this.hardwareControllerService.getControllerType(hardwareControllerSerialNumber);
        return this.controllerCommunicationServices.get(controllerType).getAverageSensorReading(hardwareControllerSerialNumber, sensorPorts);
    }

    @Override
    public void registerHardware(Hardware hardware) {
        String serialNumber = this.hardwareControllerService.getSerialNumber(hardware.getHardwareControllerId());
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        this.controllerCommunicationServices.get(controllerType).registerHardware(hardware);
    }

    @Override
    public void registerSensor(Sensor sensor) {
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        this.controllerCommunicationServices.get(controllerType).registerSensor(sensor);
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        this.logger.info("Deregistering hardware: " + hardware.getId());
        String serialNumber = this.hardwareControllerService.getSerialNumber(hardware.getHardwareControllerId());
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        this.controllerCommunicationServices.get(controllerType).deregisterHardware(hardware);
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        String controllerType = this.hardwareControllerService.getControllerType(serialNumber);
        this.controllerCommunicationServices.get(controllerType).deregisterSensor(sensor);
    }
}
