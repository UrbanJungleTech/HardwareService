package urbanjungletech.hardwareservice.jsonrpc.method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import urbanjungletech.hardwareservice.helpers.services.http.HardwareControllerTestService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetInitialStateIT {

    @Autowired
    private HardwareControllerTestService hardwareControllerTestService;

    /**
     * Given a HardwareController has been created via a POST call to /hardwarecontroller/ with the serial number "1234"
     * When a json payload of the form:
     * {
     *    "jsonrpc": "2.0",
     *    "method": "GetInitialState",
     *    "params": {
     *      "serialNumber":"1234"
     *    }
     * }
     * is delivered to the mqtt topic "FromMicrocontroller"
     * Then a json payload of the form:
     *
     */
}
