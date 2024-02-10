package urbanjungletech.hardwareservice.digitaltwins.service;

import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata;
import com.azure.digitaltwins.core.BasicRelationship;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.event.sensor.SensorCreateEvent;
import urbanjungletech.hardwareservice.event.sensor.SensorDeleteEvent;
import urbanjungletech.hardwareservice.model.sensor.Sensor;
import urbanjungletech.hardwareservice.service.query.SensorQueryService;

@Service
@ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
public class SensorTwinsService implements SpecificDigitalTwinsService<SensorCreateEvent, SensorDeleteEvent>{

    private DigitalTwinsClient digitalTwinsClient;
    private SensorQueryService sensorQueryService;

    public SensorTwinsService(DigitalTwinsClient digitalTwinsClient,
                                        SensorQueryService sensorQueryService){
        this.digitalTwinsClient = digitalTwinsClient;
        this.sensorQueryService = sensorQueryService;
    }

    @Override
    public BasicDigitalTwin createDigitalTwin(SensorCreateEvent sensorCreateEvent) {
        Sensor sensor = this.sensorQueryService.getSensor(sensorCreateEvent.getSensorId());
        String sensorModelId = "sensor-" + String.valueOf(sensor.getId());
        BasicDigitalTwin sensorDigitalTwin = new BasicDigitalTwin(sensorModelId);
        sensorDigitalTwin.setMetadata(new BasicDigitalTwinMetadata().setModelId("dtmi:urbanjungletech:Sensor;1"));
        sensorDigitalTwin.getContents().put("name", sensor.getName());
        sensorDigitalTwin.getContents().put("port", sensor.getPort());
        this.digitalTwinsClient.createOrReplaceDigitalTwin(sensorModelId, sensorDigitalTwin, BasicDigitalTwin.class);
        String hardwareControllerId = "hardwareController-" + String.valueOf(sensor.getHardwareControllerId());
        BasicDigitalTwin hardwareControllerDigitalTwin = this.digitalTwinsClient.getDigitalTwin(hardwareControllerId, BasicDigitalTwin.class);
        BasicRelationship basicRelationship = new BasicRelationship(hardwareControllerId + "To" + sensorModelId, hardwareControllerId, sensorModelId, "contains");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.digitalTwinsClient.createOrReplaceRelationship(hardwareControllerId, hardwareControllerId + "To" + sensorModelId, basicRelationship, BasicRelationship.class);
        return sensorDigitalTwin;
    }

    @Override
    public void deleteDigitalTwin(SensorDeleteEvent createEvent) {
        String sensorModelId = String.valueOf("sensor-" + createEvent.getSensorId());
        this.digitalTwinsClient.deleteDigitalTwin(sensorModelId);
    }
}
