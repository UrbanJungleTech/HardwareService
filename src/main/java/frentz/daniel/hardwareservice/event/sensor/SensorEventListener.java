package frentz.daniel.hardwareservice.event.sensor;

import frentz.daniel.hardwareservice.client.model.Sensor;
import frentz.daniel.hardwareservice.service.HardwareQueueService;
import frentz.daniel.hardwareservice.service.SensorService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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

    @EventListener
    public void handleSensorCreateEvent(SensorCreateEvent event) {
        Sensor sensor = this.sensorService.getSensor(event.getSensorId());
        hardwareQueueService.registerSensor(sensor);
    }
}
