package urbanjungletech.hardwareservice.service.tplink.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.model.ONOFF;
import urbanjungletech.hardwareservice.service.tplink.TpLinkCommandService;

@Service
public class TpLinkCommandServiceImpl implements TpLinkCommandService {

    private static final String ON_PAYLOAD = "{\"system\":{\"set_relay_state\":{\"state\":1}}}";
    private static final String OFF_PAYLOAD = "{\"system\":{\"set_relay_state\":{\"state\":0}}}";

    @Override
    public String createStateCommand(ONOFF state) {
        String payload = state == ONOFF.ON ? ON_PAYLOAD : OFF_PAYLOAD;
        return payload;
    }
}
