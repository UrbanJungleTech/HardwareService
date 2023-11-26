package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.jsonrpc.model.*;
import urbanjungletech.hardwareservice.model.Hardware;
import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.mqtt.MqttService;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

@Service()
@HardwareControllerCommunicationService(type="mqtt")
public class MqttControllerCommunicationService extends ControllerCommunicationServiceImplementation {

    private final MqttService mqttService;

    private final HardwareControllerQueryService hardwareControllerQueryService;

    public MqttControllerCommunicationService(MqttService mqttService,
                                              HardwareControllerQueryService hardwareControllerQueryService) {
        this.mqttService = mqttService;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
    }


    @Override
    public void registerHardware(Hardware hardware) {
        JsonRpcMessage jsonRpcMessage = new RegisterHardwareMessage(hardware);
        this.mqttService.publish(hardware.getHardwareControllerId(), jsonRpcMessage);
    }

    @Override
    public void registerSensor(Sensor sensor) {
        RegisterSensorMessage registerSensorMessage = new RegisterSensorMessage(sensor);
        this.mqttService.publish(sensor.getHardwareControllerId(), registerSensorMessage);
    }

    @Override
    public void deregisterHardware(Hardware hardware) {
        DeregisterHardwareMessage deregisterHardwareMessage = new DeregisterHardwareMessage(hardware);
        this.mqttService.publish(hardware.getHardwareControllerId(), deregisterHardwareMessage);
    }

    @Override
    public void deregisterSensor(Sensor sensor) {
        DeregisterSensorMessage deregisterSensorMessage = new DeregisterSensorMessage(sensor);
        this.mqttService.publish(sensor.getHardwareControllerId(), deregisterSensorMessage);
    }

    @Override
    public void sendStateToController(Hardware hardware) {
        JsonRpcMessage jsonRpcMessage = new StateChangeRpcMessage(hardware.getPort(), hardware.getDesiredState());
        this.mqttService.publish(hardware.getHardwareControllerId(), jsonRpcMessage);
    }



    @Override
    public double getSensorReading(Sensor sensor) {
        SensorPortReadingMessage message = new SensorPortReadingMessage(sensor.getPort());
        String serialNumber = this.hardwareControllerQueryService.getSerialNumber(sensor.getHardwareControllerId());
        JsonRpcMessage result = this.mqttService
                .publishWithResponse(sensor.getHardwareControllerId(), message, 10000).blockingFirst();
        return (Double) result.getResult().get("reading");
    }


}
