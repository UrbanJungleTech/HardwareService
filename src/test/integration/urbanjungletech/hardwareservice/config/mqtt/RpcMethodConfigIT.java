package urbanjungletech.hardwareservice.config.mqtt;

import urbanjungletech.hardwareservice.jsonrpc.method.RpcMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class RpcMethodConfigIT {
    @Autowired
    ApplicationContext applicationContext;

    /**
     * Given the spring application has started
     * Then there should exist a bean called rpcMethods
     * This bean should be a Map<String, RpcMethod> and contain the following mappings:
     * "urbanjungletech.hardwareservice.jsonrpc.method.ConfirmHardwareState": "ConfirmHardwareState"
     * "urbanjungletech.hardwareservice.jsonrpc.method.DeregisterHardware": DeregisterHardware
     * "urbanjungletech.hardwareservice.jsonrpc.method.DeregisterSensor": DeregisterSensor
     * "urbanjungletech.hardwareservice.jsonrpc.method.GetInitialState": GetInitialState
     * "urbanjungletech.hardwareservice.jsonrpc.method.RegisterHardware": RegisterHardware
     * "urbanjungletech.hardwareservice.jsonrpc.method.RegisterHardwareController": RegisterHardwareController
     * "urbanjungletech.hardwareservice.jsonrpc.method.RegisterSensor": RegisterSensor
     */
    @Test
    public void testRpcMethods() {
        Map<String, RpcMethod> rpcMethods = applicationContext.getBean("rpcMethods", Map.class);
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.ConfirmHardwareState", rpcMethods.get("ConfirmHardwareState").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.DeregisterHardware", rpcMethods.get("DeregisterHardware").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.DeregisterSensor", rpcMethods.get("DeregisterSensor").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.GetInitialState", rpcMethods.get("GetInitialState").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.RegisterHardware", rpcMethods.get("RegisterHardware").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.RegisterHardwareController", rpcMethods.get("RegisterHardwareController").getClass().getCanonicalName());
        assertEquals("urbanjungletech.hardwareservice.jsonrpc.method.RegisterSensor", rpcMethods.get("RegisterSensor").getClass().getCanonicalName());
    }
}
