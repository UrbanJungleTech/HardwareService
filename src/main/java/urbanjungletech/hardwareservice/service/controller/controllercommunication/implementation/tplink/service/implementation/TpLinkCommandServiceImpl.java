package urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.implementation;

import org.springframework.stereotype.Service;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.tplink.service.TpLinkCommandService;

@Service
public class TpLinkCommandServiceImpl implements TpLinkCommandService {

    private static final String ON_PAYLOAD = "{\"system\":{\"set_relay_state\":{\"state\":1}}}";
    private static final String OFF_PAYLOAD = "{\"system\":{\"set_relay_state\":{\"state\":0}}}";

    @Override
    public String createStateCommand(boolean off) {
        String payload = off == false ? ON_PAYLOAD : OFF_PAYLOAD;
        return payload;
    }
}
