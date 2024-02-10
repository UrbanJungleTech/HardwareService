package urbanjungletech.hardwareservice.digitaltwins.service;

import com.azure.digitaltwins.core.BasicDigitalTwin;
import com.azure.digitaltwins.core.BasicDigitalTwinMetadata;
import com.azure.digitaltwins.core.DigitalTwinsClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.event.hardware.HardwareCreateEvent;
import urbanjungletech.hardwareservice.event.hardware.HardwareDeleteEvent;
import urbanjungletech.hardwareservice.model.hardware.Hardware;
import urbanjungletech.hardwareservice.service.query.HardwareQueryService;

@Service
@ConditionalOnProperty(name = "digitaltwins.enabled", havingValue = "true")
public class HardwareDigitalTwinsService implements SpecificDigitalTwinsService<HardwareCreateEvent, HardwareDeleteEvent>{

    private DigitalTwinsClient digitalTwinsClient;
    private HardwareQueryService hardwareQueryService;
    public HardwareDigitalTwinsService(DigitalTwinsClient digitalTwinsClient,
                                       HardwareQueryService hardwareQueryService){
        this.digitalTwinsClient = digitalTwinsClient;
        this.hardwareQueryService = hardwareQueryService;
    }

    @Override
    public BasicDigitalTwin createDigitalTwin(HardwareCreateEvent hardwareCreateEvent) {
        Hardware hardware = this.hardwareQueryService.getHardware(hardwareCreateEvent.getHardwareId());
        String hardwareModelId = "hardware-" + String.valueOf(hardware.getId());
        BasicDigitalTwin hardwareDigitalTwin = new BasicDigitalTwin(hardwareModelId);
        hardwareDigitalTwin.setMetadata(new BasicDigitalTwinMetadata().setModelId("dtmi:urbanjungletech:Hardware;1"));
        hardwareDigitalTwin.getContents().put("name", hardware.getName());
        hardwareDigitalTwin.getContents().put("port", hardware.getPort());
        hardwareDigitalTwin.getContents().put("offState", hardware.getOffState());
        hardwareDigitalTwin.getContents().put("desiredState", hardware.getDesiredState().getState());
        hardwareDigitalTwin.getContents().put("currentState", hardware.getCurrentState().getState());
        hardwareDigitalTwin.getContents().put("possibleStates", hardware.getPossibleStates());
        this.digitalTwinsClient.createOrReplaceDigitalTwin(hardwareModelId, hardwareDigitalTwin, BasicDigitalTwin.class);
        return hardwareDigitalTwin;
    }

    @Override
    public void deleteDigitalTwin(HardwareDeleteEvent createEvent) {
        String hardwareModelId = String.valueOf(createEvent.getHardwareId());
        this.digitalTwinsClient.deleteDigitalTwin(hardwareModelId);
    }
}
