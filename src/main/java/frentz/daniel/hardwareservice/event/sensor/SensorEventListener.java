package frentz.daniel.hardwareservice.event.sensor;

import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import frentz.daniel.hardwareservice.service.SensorService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class SensorEventListener {
    private ControllerCommunicationService controllerCommunicationService;
    private SensorService sensorService;

    public SensorEventListener(ControllerCommunicationService controllerCommunicationService, SensorService sensorService) {
        this.controllerCommunicationService = controllerCommunicationService;
        this.sensorService = sensorService;
    }

    @EventListener
    public void handleSensorDeleteEvent(SensorDeleteEvent event) {
        Sensor sensor = this.sensorService.getSensor(event.getSensorId());
        controllerCommunicationService.deregisterSensor(sensor);
    }

    @Async
    @TransactionalEventListener
    public void handleSensorCreateEvent(SensorCreateEvent event) {
        Sensor sensor = this.sensorService.getSensor(event.getSensorId());
        controllerCommunicationService.registerSensor(sensor);
    }
}
