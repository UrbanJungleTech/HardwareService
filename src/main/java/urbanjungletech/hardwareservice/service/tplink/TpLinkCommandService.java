package urbanjungletech.hardwareservice.service.tplink;

import urbanjungletech.hardwareservice.model.ONOFF;

public interface TpLinkCommandService {
    String createStateCommand(ONOFF state);
}
