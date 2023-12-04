package urbanjungletech.hardwareservice.services.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.ControllerCommunicationServiceImplementation;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.HardwareControllerCommunicationService;

@Service
@HardwareControllerCommunicationService(type="test")
public class TestControllerCommunicationService extends ControllerCommunicationServiceImplementation {

}
