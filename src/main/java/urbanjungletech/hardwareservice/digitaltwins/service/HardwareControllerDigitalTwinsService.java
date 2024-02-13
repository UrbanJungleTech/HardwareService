package urbanjungletech.hardwareservice.digitaltwins.service;

import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.event.hardwarecontroller.HardwareControllerCreateEvent;
import urbanjungletech.hardwareservice.event.hardwarecontroller.HardwareControllerDeleteEvent;
import urbanjungletech.hardwareservice.model.hardwarecontroller.HardwareController;
import urbanjungletech.hardwareservice.service.query.HardwareControllerQueryService;

@Service
@ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
public class HardwareControllerDigitalTwinsService implements SpecificDigitalTwinsService<HardwareControllerCreateEvent, HardwareControllerDeleteEvent>{

    private final DigitalTwinsClient digitalTwinsClient;
    private final HardwareControllerQueryService hardwareControllerQueryService;
    private final HardwareDigitalTwinsService hardwareDigitalTwinsService;
    private final SensorTwinsService sensorTwinsService;

    public HardwareControllerDigitalTwinsService(DigitalTwinsClient digitalTwinsClient,
                                                 HardwareControllerQueryService hardwareControllerQueryService,
                                                 HardwareDigitalTwinsService hardwareDigitalTwinsService,
                                                 SensorTwinsService sensorTwinsService){
        this.digitalTwinsClient = digitalTwinsClient;
        this.hardwareControllerQueryService = hardwareControllerQueryService;
        this.hardwareDigitalTwinsService = hardwareDigitalTwinsService;
        this.sensorTwinsService = sensorTwinsService;
    }

    @Override
    public BasicDigitalTwin createDigitalTwin(HardwareControllerCreateEvent hardwareControllerCreateEvent) {
        HardwareController hardwareController = this.hardwareControllerQueryService.getHardwareController(hardwareControllerCreateEvent.getId());
        String hardwareControllerModelId = "hardwareController-" + String.valueOf(hardwareController.getId());
        BasicDigitalTwin hardwareControllerDigitalTwin = new BasicDigitalTwin(hardwareControllerModelId);
        hardwareControllerDigitalTwin.setMetadata(new BasicDigitalTwinMetadata().setModelId("dtmi:urbanjungletech:HardwareController;1"));
        hardwareControllerDigitalTwin.getContents().put("name", hardwareController.getName());
        hardwareControllerDigitalTwin.getContents().put("serialNumber", hardwareController.getSerialNumber());
        hardwareControllerDigitalTwin.getContents().put("type", hardwareController.getType());
        this.digitalTwinsClient.createOrReplaceDigitalTwin(hardwareControllerModelId, hardwareControllerDigitalTwin, BasicDigitalTwin.class);
        return hardwareControllerDigitalTwin;
    }

    @Override
    public void deleteDigitalTwin(HardwareControllerDeleteEvent createEvent) {

    }
}
