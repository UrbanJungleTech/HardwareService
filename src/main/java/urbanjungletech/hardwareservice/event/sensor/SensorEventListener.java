package urbanjungletech.hardwareservice.event.sensor;

import urbanjungletech.hardwareservice.model.Sensor;
import urbanjungletech.hardwareservice.service.controllercommunication.ControllerCommunicationService;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class SensorEventListener {
    private ControllerCommunicationService controllerCommunicationService;
    private SensorQueryService sensorQueryService;

    public SensorEventListener(ControllerCommunicationService controllerCommunicationService, SensorQueryService sensorQueryService) {
        this.controllerCommunicationService = controllerCommunicationService;
        this.sensorQueryService = sensorQueryService;
    }

    @EventListener
    public void handleSensorDeleteEvent(SensorDeleteEvent event) {
        Sensor sensor = this.sensorQueryService.getSensor(event.getSensorId());
        controllerCommunicationService.deregisterSensor(sensor);
    }

    @Async
    @TransactionalEventListener
    public void handleSensorCreateEvent(SensorCreateEvent event) {
        Sensor sensor = this.sensorQueryService.getSensor(event.getSensorId());
        controllerCommunicationService.registerSensor(sensor);
    }
}
