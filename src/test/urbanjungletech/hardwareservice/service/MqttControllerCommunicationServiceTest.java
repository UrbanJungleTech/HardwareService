package urbanjungletech.hardwareservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import urbanjungletech.hardwareservice.jsonrpc.model.StateChangeRpcMessage;
import urbanjungletech.hardwareservice.service.controller.controllercommunication.implementation.mqtt.MqttControllerCommunicationService;
import urbanjungletech.hardwareservice.service.mqtt.MqttService;

@ExtendWith(MockitoExtension.class)
class MqttControllerCommunicationServiceTest {

    @InjectMocks
    MqttControllerCommunicationService hardwareQueueService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    MqttService mqttService;

    @Captor
    ArgumentCaptor<StateChangeRpcMessage> captor;

//    @Test
//    void sendStateToController() {
//        HardwareEntity hardwareEntity = new HardwareEntity();
//        long expectedPort = 1L;
//        String serialNumber = "123456789";
//        HardwareStateEntity desiredState = new HardwareStateEntity();
//        ONOFF expectedState = ONOFF.ON;
//        int expectedStateLevel = 100;
//        desiredState.setState(expectedState);
//        desiredState.setLevel(expectedStateLevel);
//        hardwareEntity.setPort(expectedPort);
//        hardwareEntity.setDesiredState(desiredState);
//        HardwareControllerEntity hardwareControllerEntity = new HardwareControllerEntity();
//        hardwareControllerEntity.setSerialNumber(serialNumber);
//        hardwareEntity.setHardwareController(hardwareControllerEntity);
//        this.hardwareQueueService.sendStateToController(hardwareEntity);
//        verify(mqttService, times(1)).publish(eq(serialNumber), captor.capture());
//        StateChangeRpcMessage stateChangeRpcMessage = captor.getValue();
//        assertEquals(expectedPort, stateChangeRpcMessage.getParams().get("port"));
//        HardwareStateEntity resultHardwareState = (HardwareStateEntity)stateChangeRpcMessage.getParams().get("desiredState");
//        assertEquals(desiredState.getState(), resultHardwareState.getState());
//        assertEquals(desiredState.getLevel(), resultHardwareState.getLevel());
//    }

    @Test
    void sendInitialState() {
    }

    @Test
    void getSensorReading() {
    }

    @Test
    void getAverageSensorReading() {
    }
}
