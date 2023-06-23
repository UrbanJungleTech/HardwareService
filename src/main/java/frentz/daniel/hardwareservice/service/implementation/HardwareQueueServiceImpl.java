package frentz.daniel.hardwareservice.service.implementation;

import frentz.daniel.hardwareservice.client.model.Hardware;
import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.jsonrpc.model.*;
import frentz.daniel.hardwareservice.service.HardwareControllerService;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.MqttService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HardwareQueueServiceImpl implements HardwareQueueService {

    private final MqttService mqttService;

    private final HardwareControllerService hardwareControllerService;

    public HardwareQueueServiceImpl(MqttService mqttService,
                                    HardwareControllerService hardwareControllerService) {
        this.mqttService = mqttService;
        this.hardwareControllerService = hardwareControllerService;
    }


    @Override
    public void registerHardware(Hardware hardware) {
        JsonRpcMessage jsonRpcMessage = new RegisterHardwareMessage(hardware);
        String serialNumber = this.hardwareControllerService.getSerialNumber(hardware.getHardwareControllerId());
        this.mqttService.publish(serialNumber, jsonRpcMessage);
    }

    @Override
    public void registerSensor(Sensor sensor) {
        RegisterSensorMessage registerSensorMessage = new RegisterSensorMessage(sensor);
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        this.mqttService.publish(serialNumber, registerSensorMessage);
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardware);
        String serialNumber = this.hardwareControllerService.getSerialNumber(hardware.getHardwareControllerId());
        this.mqttService.publish(serialNumber, deregisterHardwareMessage);
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensor);
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        this.mqttService.publish(serialNumber, deregisterSensorMessage);
    }

    @Override
    public void sendStateToController(Hardware hardware) {
        JsonRpcMessage jsonRpcMessage = new StateChangeRpcMessage(hardware.getPort(), hardware.getDesiredState());
        String serialNumber = this.hardwareControllerService.getSerialNumber(hardware.getHardwareControllerId());
        this.mqttService.publish(serialNumber, jsonRpcMessage);
    }

    @Override
    public void sendInitialState(String serialNumber, List<Hardware> hardwares) {
        List<HardwareStateRpcMessage> hardwareStateRpcMessages = new ArrayList<>();
        for (Hardware hardware : hardwares) {
            HardwareStateRpcMessage hardwareStateRpcMessage = new HardwareStateRpcMessage(hardware.getPort(), hardware.getDesiredState());
            hardwareStateRpcMessages.add(hardwareStateRpcMessage);
        }
        InitialStateRpcMessage initialStateRpcMessage = new InitialStateRpcMessage(hardwareStateRpcMessages);
        this.mqttService.publish(serialNumber, initialStateRpcMessage);
    }


    @Override
    public double getSensorReading(Sensor sensor) {
        SensorPortReadingMessage message = new SensorPortReadingMessage(sensor.getPort());
        String serialNumber = this.hardwareControllerService.getSerialNumber(sensor.getHardwareControllerId());
        JsonRpcMessage result = this.mqttService
                .publishWithResponse(serialNumber, message, 10000).blockingFirst();
//        this.mqttService.publish(serialNumber, message);
//        JsonRpcMessage result = new JsonRpcMessage();
//        result.setResult(Map.of("reading", 0.0));
        return (Double) result.getResult().get("reading");
    }

    @Override
    public double getAverageSensorReading(String hardwareControllerSerialNumber, long[] sensorPorts) {
        AverageSensorReadingMessage message = new AverageSensorReadingMessage(sensorPorts);
        JsonRpcMessage reading = this.mqttService
                .publishWithResponse(hardwareControllerSerialNumber, message, 10000).blockingFirst();
        return (Double) reading.getResult().get("reading");
    }

}
