package frentz.daniel.hardwareservice.service;

import frentz.daniel.hardwareservice.converter.HardwareStateConverter;
import frentz.daniel.hardwareservice.entity.HardwareEntity;
import frentz.daniel.hardwareservice.entity.SensorEntity;
import frentz.daniel.hardwareservice.jsonrpc.model.*;
import frentz.daniel.hardwareservice.client.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HardwareQueueServiceImpl implements HardwareQueueService {

    private MqttService mqttService;

    private HardwareStateConverter hardwareStateConverter;

    public HardwareQueueServiceImpl(MqttService mqttService,
                                    HardwareStateConverter hardwareStateConverter) {
        this.mqttService = mqttService;
        this.hardwareStateConverter = hardwareStateConverter;
    }


    @Override
    public void registerHardware(HardwareEntity hardware) {
        JsonRpcMessage jsonRpcMessage = new RegisterHardwareMessage(hardware);
        this.mqttService.publish(hardware.getHardwareController().getSerialNumber(), jsonRpcMessage);
    }

    @Override
    public void registerSensor(SensorEntity sensor) {
        RegisterSensorMessage registerSensorMessage = new RegisterSensorMessage(sensor);
        this.mqttService.publish(sensor.getHardwareController().getSerialNumber(), registerSensorMessage);
    }

    @Override
    public void deregisterHardware(HardwareEntity hardware) {
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardware);
        this.mqttService.publish(hardware.getHardwareController().getSerialNumber(), deregisterHardwareMessage);
    }

    @Override
    public void deregisterSensor(SensorEntity sensor) {
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensor);
        this.mqttService.publish(sensor.getHardwareController().getSerialNumber(), deregisterSensorMessage);
    }

    @Override
    public void sendStateToController(HardwareEntity hardware) {
        JsonRpcMessage jsonRpcMessage = new StateChangeRpcMessage(hardware.getPort(), hardware.getDesiredState());
        this.mqttService.publish(hardware.getHardwareController().getSerialNumber(), jsonRpcMessage);
    }

    @Override
    public void sendInitialState(String serialNumber, List<HardwareEntity> hardwares) {
        List<HardwareStateRpcMessage> hardwareStateRpcMessages = new ArrayList<>();
        for (HardwareEntity hardware : hardwares) {
            HardwareState desiredState = this.hardwareStateConverter.toModel(hardware.getDesiredState());
            HardwareStateRpcMessage hardwareStateRpcMessage = new HardwareStateRpcMessage(hardware.getPort(), desiredState);
            hardwareStateRpcMessages.add(hardwareStateRpcMessage);
        }
        InitialStateRpcMessage initialStateRpcMessage = new InitialStateRpcMessage(hardwareStateRpcMessages);
        this.mqttService.publish(serialNumber, initialStateRpcMessage);
    }


    @Override
    public double getSensorReading(SensorEntity sensorEntity) {
        SensorPortReadingMessage message = new SensorPortReadingMessage(sensorEntity.getPort());
        JsonRpcMessage result = this.mqttService
                .publishWithResponse(sensorEntity.getHardwareController().getSerialNumber(), message, 10000).blockingFirst();
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
