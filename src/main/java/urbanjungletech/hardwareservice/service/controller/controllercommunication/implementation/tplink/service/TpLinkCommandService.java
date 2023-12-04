package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service;

import urbanjungletech.hardwareservice.model.ONOFF;

public interface TpLinkCommandService {
    String createStateCommand(ONOFF state);
}
