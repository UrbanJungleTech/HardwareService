package urbanjungletech.hardwareservice.jsonrpc.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AverageSensorReadingMessageTest {

    @Test
    void testParametersSetOnConstruction(){
        String[] ports = new String[]{"1","2"};
        AverageSensorReadingMessage averageSensorReadingMessage = new AverageSensorReadingMessage(ports);
        String method = averageSensorReadingMessage.getMethod();
        assertEquals("ReadAverageSensor", method);
        long[] setPorts = (long[])averageSensorReadingMessage.getParams().get("ports");
        assertTrue(Arrays.stream(setPorts).anyMatch(port -> {return port == 1;}));
        assertTrue(Arrays.stream(setPorts).anyMatch(port -> {return port == 2;}));
    }
}
