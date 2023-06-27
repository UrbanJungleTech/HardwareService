package frentz.daniel.hardwareservice.event.sensor;

import frentz.daniel.hardwareservice.model.Sensor;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.SensorService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class SensorEventListener {
    private HardwareQueueService hardwareQueueService;
    private SensorService sensorService;

    public SensorEventListener(HardwareQueueService hardwareQueueService, SensorService sensorService) {
        this.hardwareQueueService = hardwareQueueService;
        this.sensorService = sensorService;
    }

    @EventListener
    public void handleSensorDeleteEvent(SensorDeleteEvent event) {
        Sensor sensor = this.sensorService.getSensor(event.getSensorId());
        hardwareQueueService.deregisterSensor(sensor);
    }

    @Async
    @TransactionalEventListener
    public void handleSensorCreateEvent(SensorCreateEvent event) {
        Sensor sensor = this.sensorService.getSensor(event.getSensorId());
        hardwareQueueService.registerSensor(sensor);
    }
}
